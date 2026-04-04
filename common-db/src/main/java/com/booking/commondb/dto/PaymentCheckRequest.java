package com.booking.commondb.dto;

import java.math.BigDecimal;

public record PaymentCheckRequest(
        Long bookingId,
        String person,
        BigDecimal amount
) {}
