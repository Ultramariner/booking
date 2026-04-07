package com.booking.registrator.service;

import com.booking.commondb.entity.Apartment;
import com.booking.commondb.entity.BookingInfo;
import com.booking.commondb.dto.BookingRequestDb;
import com.booking.feignclients.dto.BookingResponse;

import java.util.Optional;

public interface RegistrationService {

    BookingResponse register(BookingRequestDb request);

    Optional<Apartment> checkApartments();

    BookingInfo createBooking(Apartment apartment, BookingRequestDb request);

    BookingResponse checkPayment(BookingInfo booking);
}
