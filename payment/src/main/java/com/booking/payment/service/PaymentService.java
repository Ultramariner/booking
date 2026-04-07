package com.booking.payment.service;

import com.booking.commondb.dto.PaymentCheckRequestDb;
import com.booking.feignclients.dto.PaymentCheckResponse;

public interface PaymentService {

    PaymentCheckResponse checkPayment(PaymentCheckRequestDb request);
}
