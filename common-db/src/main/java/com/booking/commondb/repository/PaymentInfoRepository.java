package com.booking.commondb.repository;

import com.booking.commondb.entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, UUID> {
}
