package com.booking.commonkafka.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentCheckedEvent {
    private Long bookingId;
    private boolean paid;
    private UUID paymentUid;
}

