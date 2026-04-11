package com.booking.registrator.service.producer;

import com.booking.commonkafka.Topics;
import com.booking.commonkafka.dto.BookingCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingCompletedProducer {

    private final KafkaTemplate<String, Object> kafka;

    public void send(BookingCompletedEvent event) {
        kafka.send(Topics.BOOKING_COMPLETED, event);
    }
}
