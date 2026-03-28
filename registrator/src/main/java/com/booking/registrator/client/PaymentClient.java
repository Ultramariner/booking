package com.booking.registrator.client;

import com.booking.commondb.dto.PaymentCheckRequest;
import com.booking.commondb.dto.PaymentCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PaymentClient {

    private final RestTemplate restTemplate;

    @Value("${services.payment.url}")
    private String paymentUrl;

    public PaymentCheckResponse checkPayment(PaymentCheckRequest request) {
        return restTemplate.postForObject(
                paymentUrl + "/payment/check",
                request,
                PaymentCheckResponse.class
        );
    }
}
