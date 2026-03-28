package com.booking.generator.client;

import com.booking.commondb.dto.BookingRequest;
import com.booking.commondb.dto.BookingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RegistratorClient {

    private final RestTemplate restTemplate;

    @Value("${services.registrator.url}")
    private String registratorUrl;

    public BookingResponse sendBooking(Long id, String booker) {
        BookingRequest request = new BookingRequest(id, booker);

        return restTemplate.postForObject(
                registratorUrl + "/register",
                request,
                BookingResponse.class
        );
    }
}

