package com.booking.registrator.service.producer;

import com.booking.commonkafka.Topics;
import com.booking.commonkafka.dto.BookingRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingRegisteredProducer {

    private final KafkaTemplate<String, Object> kafka;

    public void send(BookingRegisteredEvent event) {
        kafka.send(Topics.BOOKING_REGISTERED, event);
    }
}
