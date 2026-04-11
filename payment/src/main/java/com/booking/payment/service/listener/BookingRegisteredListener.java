package com.booking.payment.service.listener;

import com.booking.commonkafka.Topics;
import com.booking.commonkafka.dto.BookingRegisteredEvent;
import com.booking.payment.service.PaymentService;
import com.booking.payment.service.producer.PaymentCheckedProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingRegisteredListener {

    private final PaymentService paymentService;
    private final PaymentCheckedProducer paymentCheckedProducer;

    @KafkaListener(topics = Topics.BOOKING_REGISTERED, groupId = "payment")
    public void handle(BookingRegisteredEvent event) {

        var response = paymentService.checkPaymentFromKafka(event);

        paymentCheckedProducer.send(response);
    }
}
