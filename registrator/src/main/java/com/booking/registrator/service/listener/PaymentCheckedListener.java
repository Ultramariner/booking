package com.booking.registrator.service.listener;

import com.booking.commonkafka.Topics;
import com.booking.commonkafka.dto.PaymentCheckedEvent;
import com.booking.registrator.service.RegistrationService;
import com.booking.registrator.service.producer.BookingCompletedProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentCheckedListener {

    private final RegistrationService registrationService;
    private final BookingCompletedProducer bookingCompletedProducer;

    @KafkaListener(topics = Topics.PAYMENT_CHECKED, groupId = "registrator")
    public void handle(PaymentCheckedEvent event) {

        var completed = registrationService.completeBookingFromKafka(event);

        bookingCompletedProducer.send(completed);
    }
}
