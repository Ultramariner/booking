package com.booking.registrator.service.impl;

import com.booking.commondb.entity.Apartment;
import com.booking.commondb.entity.BookingInfo;
import com.booking.commondb.entity.BookingStatus;
import com.booking.commondb.entity.Resident;
import com.booking.commondb.repository.ApartmentRepository;
import com.booking.commondb.repository.BookingInfoRepository;
import com.booking.commondb.repository.ResidentRepository;
import com.booking.feignclients.clients.PaymentClient;
import com.booking.feignclients.dto.BookingResponse;
import com.booking.feignclients.dto.PaymentCheckRequest;
import com.booking.feignclients.dto.PaymentCheckResponse;
import com.booking.commondb.dto.*;
import com.booking.registrator.mapper.PaymentRequestMapper;
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
    private final PaymentRequestMapper paymentRequestMapper;

    @Override
    public BookingResponse register(BookingRequestDb request) {

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
    public BookingInfo createBooking(Apartment apartment, BookingRequestDb request) {

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

        PaymentCheckRequestDb dbDto = new PaymentCheckRequestDb(
                booking.getId(),
                booking.getResident().getName(),
                BigDecimal.valueOf(1000)
        );

        PaymentCheckRequest feignDto = paymentRequestMapper.toFeign(dbDto);

        PaymentCheckResponse paymentResponse = paymentClient.checkPayment(feignDto);

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
