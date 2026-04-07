package com.booking.commondb.dto;

import java.math.BigDecimal;

public record PaymentCheckRequestDb(
        Long bookingId,
        String person,
        BigDecimal amount
) {}
