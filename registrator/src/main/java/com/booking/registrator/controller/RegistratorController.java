package com.booking.registrator.controller;

import com.booking.commondb.dto.BookingRequest;
import com.booking.commondb.dto.BookingResponse;
import com.booking.registrator.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registrator")
@RequiredArgsConstructor
public class RegistratorController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<BookingResponse> register(@RequestBody BookingRequest request) {
        return ResponseEntity.ok(registrationService.register(request));
    }

}
