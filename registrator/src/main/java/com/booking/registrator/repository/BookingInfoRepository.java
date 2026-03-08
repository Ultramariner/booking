package com.booking.registrator.repository;

import com.booking.registrator.entity.BookingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingInfoRepository extends JpaRepository<BookingInfo, Long> {
}
