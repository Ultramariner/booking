package com.booking.registrator.service;

import com.booking.commondb.entity.Apartment;
import com.booking.commondb.entity.BookingInfo;
import com.booking.commondb.dto.BookingRequest;
import com.booking.commondb.dto.BookingResponse;

import java.util.Optional;

public interface RegistrationService {

    BookingResponse register(BookingRequest request);

    Optional<Apartment> checkApartments();

    BookingInfo createBooking(Apartment apartment, BookingRequest request);

    BookingResponse checkPayment(BookingInfo booking);
}
