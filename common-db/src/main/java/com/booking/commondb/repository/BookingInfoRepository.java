package com.booking.commondb.repository;

import com.booking.commondb.entity.BookingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingInfoRepository extends JpaRepository<BookingInfo, Long> {
}
