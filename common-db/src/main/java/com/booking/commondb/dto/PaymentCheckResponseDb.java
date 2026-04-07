package com.booking.commondb.dto;

import java.util.UUID;

public record PaymentCheckResponseDb(
        boolean paid,
        Long bookingId,
        UUID paymentUid
) {}