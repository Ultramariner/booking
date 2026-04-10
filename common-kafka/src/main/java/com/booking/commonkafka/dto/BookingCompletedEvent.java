package com.booking.commonkafka.dto;

import lombok.Data;

@Data
public class BookingCompletedEvent {
    private Long bookingId;
    private boolean success;
}
