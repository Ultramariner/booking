package com.booking.generator.service.producer;

import com.booking.commonkafka.Topics;
import com.booking.commonkafka.dto.BookingCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingCreatedProducer {

    private final KafkaTemplate<String, Object> kafka;

    public void send(BookingCreatedEvent event) {
        kafka.send(Topics.BOOKING_CREATED, event);
    }
}