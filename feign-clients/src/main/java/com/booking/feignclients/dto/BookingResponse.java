package com.booking.feignclients.dto;

public record BookingResponse(
        boolean success,
        Long bookingId
) {}