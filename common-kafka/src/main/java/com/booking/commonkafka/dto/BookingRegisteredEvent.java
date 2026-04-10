package com.booking.commonkafka.dto;

import lombok.Data;

@Data
public class BookingRegisteredEvent {
    private Long bookingId;
    private boolean registered;
}
