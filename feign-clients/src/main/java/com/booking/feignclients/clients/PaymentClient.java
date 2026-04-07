package com.booking.feignclients.clients;

import com.booking.feignclients.config.FeignConfig;
import com.booking.feignclients.config.FeignErrorDecoderConfig;
import com.booking.feignclients.config.FeignRetryConfig;
import com.booking.feignclients.dto.PaymentCheckRequest;
import com.booking.feignclients.dto.PaymentCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-service",
        url = "${services.payment.url}",
        path = "/payment",
        configuration = {FeignConfig.class, FeignRetryConfig.class, FeignErrorDecoderConfig.class}
)
public interface PaymentClient {

    @PostMapping("/check")
    PaymentCheckResponse checkPayment(@RequestBody PaymentCheckRequest request);
}
