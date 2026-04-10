package com.booking.commonkafka.dto;

import lombok.Data;

@Data
public class BookingCreatedEvent {
    private Long bookingId;
    private Long apartmentId;
    private String resident;
}
