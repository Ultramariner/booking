package com.booking.feignclients.clients;

import com.booking.feignclients.config.FeignConfig;
import com.booking.feignclients.config.FeignErrorDecoderConfig;
import com.booking.feignclients.config.FeignRetryConfig;
import com.booking.feignclients.dto.BookingRequest;
import com.booking.feignclients.dto.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "registrator-service",
        url = "${services.registrator.url}",
        path = "/registrator",
        configuration = {FeignConfig.class, FeignRetryConfig.class, FeignErrorDecoderConfig.class}
)
public interface RegistratorClient {

    @PostMapping("/register")
    BookingResponse sendBooking(@RequestBody BookingRequest request);
}
