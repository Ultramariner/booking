package com.booking.payment.service.impl;

import com.booking.commondb.dto.PaymentCheckRequest;
import com.booking.commondb.dto.PaymentCheckResponse;
import com.booking.commondb.entity.PaymentInfo;
import com.booking.commondb.entity.PaymentStatus;
import com.booking.commondb.repository.PaymentInfoRepository;
import com.booking.payment.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentInfoRepository paymentInfoRepository;

    @Value("${payment.successProbability}")
    private double successProbability;

    @Override
    public PaymentCheckResponse checkPayment(PaymentCheckRequest request) {

        boolean approved = ThreadLocalRandom.current().nextDouble() <= successProbability;

        PaymentInfo info = new PaymentInfo();
        info.setUid(UUID.randomUUID());
        info.setBookingId(request.bookingId());
        info.setPerson(request.person());
        info.setAmount(request.amount());
        info.setProcessedAt(Instant.now());

        if (approved) {
            info.setStatus(PaymentStatus.APPROVED);
            paymentInfoRepository.save(info);

            return new PaymentCheckResponse(true, info.getBookingId(), info.getUid());
        }

        info.setStatus(PaymentStatus.DENIED);
        paymentInfoRepository.save(info);

        return new PaymentCheckResponse(false, info.getBookingId(), info.getUid());
    }
}
