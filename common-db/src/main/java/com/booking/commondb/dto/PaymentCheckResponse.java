package com.booking.commondb.dto;

import java.util.UUID;

public record PaymentCheckResponse(
        boolean paid,
        Long bookingId,
        UUID paymentUid
) {}