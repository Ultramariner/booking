package com.booking.commondb.dto;

public record BookingResponse(
        boolean success,
        Long bookingId
) {}