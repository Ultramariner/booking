package com.booking.registrator.service.impl;

import com.booking.commondb.entity.Apartment;
import com.booking.commondb.entity.BookingInfo;
import com.booking.commondb.entity.BookingStatus;
import com.booking.commondb.entity.Resident;
import com.booking.commondb.repository.ApartmentRepository;
import com.booking.commondb.repository.BookingInfoRepository;
import com.booking.commondb.repository.ResidentRepository;
import com.booking.registrator.client.PaymentClient;
import com.booking.commondb.dto.*;
import com.booking.registrator.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final ApartmentRepository apartmentRepository;
    private final ResidentRepository residentRepository;
    private final BookingInfoRepository bookingInfoRepository;
    private final PaymentClient paymentClient;

    @Override
    public BookingResponse register(BookingRequest request) {

        Optional<Apartment> optional = checkApartments();
        if (optional.isEmpty()) {
            return new BookingResponse(false, null);
        }

        Apartment apartment = optional.get();

        BookingInfo booking = createBooking(apartment, request);

        return checkPayment(booking);
    }

    @Override
    public Optional<Apartment> checkApartments() {
        return apartmentRepository.findFirstByIsVacantTrue();
    }

    @Override
    public BookingInfo createBooking(Apartment apartment, BookingRequest request) {

        apartment.setIsVacant(false);
        apartmentRepository.save(apartment);

        Resident resident = new Resident();
        resident.setGeneratedId(request.id());
        resident.setName(request.booker());
        residentRepository.save(resident);

        BookingInfo booking = new BookingInfo();
        booking.setApartment(apartment);
        booking.setResident(resident);
        booking.setBookedAt(Instant.now());

        return bookingInfoRepository.save(booking);
    }

    @Override
    public BookingResponse checkPayment(BookingInfo booking) {

        PaymentCheckResponse paymentResponse = paymentClient.checkPayment(
                new PaymentCheckRequest(
                        booking.getId(),
                        booking.getResident().getName(),
                        BigDecimal.valueOf(1000)
                )
        );

        booking.setPaymentUid(paymentResponse.paymentUid());

        if (!paymentResponse.paid()) {
            booking.setBookingStatus(BookingStatus.DENIED);
            bookingInfoRepository.save(booking);

            Apartment apartment = booking.getApartment();
            apartment.setIsVacant(true);
            apartmentRepository.save(apartment);

            return new BookingResponse(false, booking.getId());
        }

        booking.setBookingStatus(BookingStatus.APPROVED);
        bookingInfoRepository.save(booking);

        return new BookingResponse(true, booking.getId());
    }
}
