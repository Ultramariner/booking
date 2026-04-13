package com.booking.registrator.service.impl;

import com.booking.commondb.entity.Apartment;
import com.booking.commondb.entity.BookingInfo;
import com.booking.commondb.entity.BookingStatus;
import com.booking.commondb.entity.Resident;
import com.booking.commondb.repository.ApartmentRepository;
import com.booking.commondb.repository.BookingInfoRepository;
import com.booking.commondb.repository.ResidentRepository;
import com.booking.commonkafka.dto.BookingCompletedEvent;
import com.booking.commonkafka.dto.BookingCreatedEvent;
import com.booking.commonkafka.dto.BookingRegisteredEvent;
import com.booking.commonkafka.dto.PaymentCheckedEvent;
import com.booking.feignclients.clients.PaymentClient;
import com.booking.feignclients.dto.BookingResponse;
import com.booking.feignclients.dto.PaymentCheckRequest;
import com.booking.feignclients.dto.PaymentCheckResponse;
import com.booking.commondb.dto.*;
import com.booking.registrator.mapper.PaymentRequestMapper;
import com.booking.registrator.service.RegistrationService;
import jakarta.transaction.Transactional;
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
        apartment = apartmentRepository.save(apartment);

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

    @Override
    @Transactional
    public BookingRegisteredEvent registerFromKafka(BookingCreatedEvent event) {

        Optional<Apartment> optional = checkApartments();
        if (optional.isEmpty()) {
            BookingRegisteredEvent failed = new BookingRegisteredEvent();
            failed.setBookingId(event.getBookingId());
            failed.setRegistered(false);
            return failed;
        }

        Apartment apartment = optional.get();

        BookingRequestDb request = new BookingRequestDb(
                event.getBookingId(),
                event.getResident()
        );

        BookingInfo booking = createBooking(apartment, request);

        BookingRegisteredEvent registered = new BookingRegisteredEvent();
        registered.setBookingId(booking.getId());
        registered.setRegistered(true);
        registered.setPerson(booking.getResident().getName());

        return registered;
    }

    @Override
    public BookingCompletedEvent completeBookingFromKafka(PaymentCheckedEvent event) {

        BookingInfo booking = bookingInfoRepository.findById(event.getBookingId())
                .orElseThrow(() -> new IllegalStateException("Booking not found: " + event.getBookingId()));

        boolean paid = event.isPaid();

        if (!paid) {
            booking.setBookingStatus(BookingStatus.DENIED);

            Apartment apartment = booking.getApartment();
            apartment.setIsVacant(true);
            apartmentRepository.save(apartment);

        } else {
            booking.setBookingStatus(BookingStatus.APPROVED);
        }

        booking.setPaymentUid(event.getPaymentUid());
        bookingInfoRepository.save(booking);

        BookingCompletedEvent completed = new BookingCompletedEvent();
        completed.setBookingId(booking.getId());
        completed.setSuccess(paid);

        return completed;
    }
}
