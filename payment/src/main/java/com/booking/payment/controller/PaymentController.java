package com.booking.payment.controller;

import com.booking.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registrator")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/checkPayment")
    public ResponseEntity<Void> checkPayment() {
        paymentService.checkPayment();
        return ResponseEntity.ok().build();
    }
}
