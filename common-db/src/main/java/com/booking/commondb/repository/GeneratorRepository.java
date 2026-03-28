package com.booking.commondb.repository;

import com.booking.commondb.entity.GeneratedEntity;
import com.booking.commondb.entity.GeneratedEntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneratorRepository extends JpaRepository<GeneratedEntity, Long> {

    List<GeneratedEntity> findAllByStatus(GeneratedEntityStatus status);
}
