package com.booking.generator.service.impl;

import com.booking.commondb.dto.BookingResponse;
import com.booking.commondb.entity.GeneratedEntity;
import com.booking.commondb.entity.GeneratedEntityStatus;
import com.booking.commondb.repository.GeneratorRepository;
import com.booking.generator.client.RegistratorClient;
import com.booking.generator.service.GeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {

    private final GeneratorRepository generatorRepository;
    private final RegistratorClient registratorClient;

    @Override
    public void generate() {
        GeneratedEntity entity = new GeneratedEntity();
        entity.setBooker(generateRandomBooker());
        entity.setStatus(GeneratedEntityStatus.CREATED);
        entity.setCreatedAt(Instant.now());
        entity.setLastModifiedAt(Instant.now());

        generatorRepository.save(entity);
    }

    @Override
    public void book() {
        Optional<GeneratedEntity> optional =
                generatorRepository.findFirstByStatusOrderByCreatedAtAsc(GeneratedEntityStatus.CREATED);

        if (optional.isEmpty()) {
            return;
        }

        GeneratedEntity entity = optional.get();

        try {
            BookingResponse response =
                    registratorClient.sendBooking(entity.getId(), entity.getBooker());

            if (response.success()) {
                entity.setStatus(GeneratedEntityStatus.BOOKED);
            } else {
                entity.setStatus(GeneratedEntityStatus.DENIED);
            }

            entity.setLastModifiedAt(Instant.now());

        } catch (Exception ex) {
            entity.setStatus(GeneratedEntityStatus.FAILED);
            entity.setLastModifiedAt(Instant.now());
        }

        generatorRepository.save(entity);
    }

    private String generateRandomBooker() {
        int num = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "Client" + num;
    }

}
