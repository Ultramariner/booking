package com.booking.payment.controller;

import com.booking.commondb.dto.PaymentCheckRequestDb;
import com.booking.feignclients.dto.PaymentCheckResponse;
import com.booking.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/check")
    public ResponseEntity<PaymentCheckResponse> checkPayment(
            @RequestBody PaymentCheckRequestDb request
    ) {
        PaymentCheckResponse response = paymentService.checkPayment(request);
        return ResponseEntity.ok(response);
    }
}
