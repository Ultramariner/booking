package com.booking.commondb.dto;

public record PaymentCheckRequest(
        Long bookingId,
        String person,
        Double amount
) {}
