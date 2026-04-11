package com.booking.generator.service.listener;

import com.booking.commonkafka.Topics;
import com.booking.commonkafka.dto.BookingCompletedEvent;
import com.booking.commondb.entity.GeneratedEntity;
import com.booking.commondb.entity.GeneratedEntityStatus;
import com.booking.commondb.repository.GeneratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class BookingCompletedListener {

    private final GeneratorRepository generatorRepository;

    @KafkaListener(topics = Topics.BOOKING_COMPLETED, groupId = "generator")
    public void handle(BookingCompletedEvent event) {

        GeneratedEntity entity = generatorRepository.findById(event.getBookingId())
                .orElseThrow(() -> new IllegalStateException("Generated entity not found: " + event.getBookingId()));

        entity.setStatus(event.isSuccess()
                ? GeneratedEntityStatus.BOOKED
                : GeneratedEntityStatus.DENIED);

        entity.setLastModifiedAt(Instant.now());
        generatorRepository.save(entity);
    }
}
