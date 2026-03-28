package com.booking.payment.service;

import com.booking.commondb.dto.PaymentCheckRequest;
import com.booking.commondb.dto.PaymentCheckResponse;

public interface PaymentService {

    PaymentCheckResponse checkPayment(PaymentCheckRequest request);
}
