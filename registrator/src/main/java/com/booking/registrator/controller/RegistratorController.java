package com.booking.registrator.controller;

import com.booking.registrator.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registrator")
@RequiredArgsConstructor
public class RegistratorController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<Void> register() {
        registrationService.register();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkPayment")
    public ResponseEntity<Void> checkPayment() {
        registrationService.checkPayment();
        return ResponseEntity.ok().build();
    }
}
