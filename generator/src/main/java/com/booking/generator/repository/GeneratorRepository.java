package com.booking.generator.repository;

import com.booking.generator.entity.GeneratedEntity;
import com.booking.generator.entity.GeneratedEntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneratorRepository extends JpaRepository<GeneratedEntity, Long> {

    List<GeneratedEntity> findAllByStatus(GeneratedEntityStatus status);
}
