package com.booking.registrator.service.listener;

import com.booking.commonkafka.Topics;
import com.booking.commonkafka.dto.BookingCreatedEvent;
import com.booking.registrator.service.RegistrationService;
import com.booking.registrator.service.producer.BookingRegisteredProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingCreatedListener {

    private final RegistrationService registrationService;
    private final BookingRegisteredProducer bookingRegisteredProducer;

    @KafkaListener(topics = Topics.BOOKING_CREATED, groupId = "registrator")
    public void handle(BookingCreatedEvent event) {

        var response = registrationService.registerFromKafka(event);

        bookingRegisteredProducer.send(response);
    }
}
