package com.booking.registrator.service;

import com.booking.commondb.entity.Apartment;
import com.booking.commondb.entity.BookingInfo;
import com.booking.commondb.dto.BookingRequestDb;
import com.booking.commonkafka.dto.BookingCompletedEvent;
import com.booking.commonkafka.dto.BookingCreatedEvent;
import com.booking.commonkafka.dto.BookingRegisteredEvent;
import com.booking.commonkafka.dto.PaymentCheckedEvent;
import com.booking.feignclients.dto.BookingResponse;

import java.util.Optional;

public interface RegistrationService {

    BookingResponse register(BookingRequestDb request);

    Optional<Apartment> checkApartments();

    BookingInfo createBooking(Apartment apartment, BookingRequestDb request);

    BookingResponse checkPayment(BookingInfo booking);

    BookingRegisteredEvent registerFromKafka(BookingCreatedEvent event);

    BookingCompletedEvent completeBookingFromKafka(PaymentCheckedEvent event);
}
