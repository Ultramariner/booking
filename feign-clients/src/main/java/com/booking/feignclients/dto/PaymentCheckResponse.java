package com.booking.feignclients.dto;

import java.util.UUID;

public record PaymentCheckResponse(
        boolean paid,
        Long bookingId,
        UUID paymentUid
) {}