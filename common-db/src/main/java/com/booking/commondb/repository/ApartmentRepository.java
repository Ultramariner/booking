package com.booking.commondb.repository;

import com.booking.commondb.entity.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    Optional<Apartment> findFirstByIsVacantTrue();
}
