package com.booking.payment.service.producer;

import com.booking.commonkafka.Topics;
import com.booking.commonkafka.dto.PaymentCheckedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentCheckedProducer {

    private final KafkaTemplate<String, Object> kafka;

    public void send(PaymentCheckedEvent event) {
        kafka.send(Topics.PAYMENT_CHECKED, event);
    }
}
