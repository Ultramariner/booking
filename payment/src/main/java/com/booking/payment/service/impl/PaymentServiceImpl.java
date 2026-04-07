package com.booking.payment.service.impl;

import com.booking.commondb.dto.PaymentCheckRequestDb;
import com.booking.commondb.dto.PaymentCheckResponseDb;
import com.booking.commondb.entity.PaymentInfo;
import com.booking.commondb.entity.PaymentStatus;
import com.booking.commondb.repository.PaymentInfoRepository;
import com.booking.feignclients.dto.PaymentCheckResponse;
import com.booking.payment.mapper.PaymentResponseMapper;
import com.booking.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentInfoRepository paymentInfoRepository;
    private final PaymentResponseMapper paymentResponseMapper;

    @Value("${payment.successProbability}")
    private double successProbability;

    @Override
    public PaymentCheckResponse checkPayment(PaymentCheckRequestDb request) {

        boolean approved = ThreadLocalRandom.current().nextDouble() <= successProbability;

        PaymentInfo info = new PaymentInfo();
        info.setBookingId(request.bookingId());
        info.setPerson(request.person());
        info.setAmount(request.amount());
        info.setProcessedAt(Instant.now());
        info.setStatus(approved ? PaymentStatus.APPROVED : PaymentStatus.DENIED);

        paymentInfoRepository.save(info);

        PaymentCheckResponseDb dbDto = new PaymentCheckResponseDb(
                approved,
                info.getBookingId(),
                info.getUid()
        );

        return paymentResponseMapper.toFeign(dbDto);
    }
}
