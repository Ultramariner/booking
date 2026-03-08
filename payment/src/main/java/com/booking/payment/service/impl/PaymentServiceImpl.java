package com.booking.payment.service.impl;

import com.booking.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentService paymentService;

    @Override
    public void checkPayment() {

    }
}
