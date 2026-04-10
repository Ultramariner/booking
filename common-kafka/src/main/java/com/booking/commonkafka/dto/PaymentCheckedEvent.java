package com.booking.commonkafka.dto;

import lombok.Data;

@Data
public class PaymentCheckedEvent {
    private Long bookingId;
    private boolean paid;
}

