package com.booking.feignclients.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        log.error("Feign error: method={}, status={}, reason={}", methodKey, response.status(), response.reason());

        return switch (response.status()) {
            case 400 -> new IllegalArgumentException("Bad request");
            case 404 -> new RuntimeException("Not found");
            case 500 -> new IllegalStateException("Server error");
            default -> new RuntimeException("Unexpected error");
        };
    }
}
