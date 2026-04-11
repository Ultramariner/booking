package com.booking.payment.service;

import com.booking.commondb.dto.PaymentCheckRequestDb;
import com.booking.commonkafka.dto.BookingRegisteredEvent;
import com.booking.commonkafka.dto.PaymentCheckedEvent;
import com.booking.feignclients.dto.PaymentCheckResponse;

public interface PaymentService {

    PaymentCheckResponse checkPayment(PaymentCheckRequestDb request);

    PaymentCheckedEvent checkPaymentFromKafka(BookingRegisteredEvent event);
}
