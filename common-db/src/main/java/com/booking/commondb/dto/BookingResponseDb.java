package com.booking.commondb.dto;

public record BookingResponseDb(
        boolean success,
        Long bookingId
) {}