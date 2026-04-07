package com.booking.generator.service.impl;

import com.booking.commondb.dto.BookingRequestDb;
import com.booking.commondb.entity.GeneratedEntity;
import com.booking.commondb.entity.GeneratedEntityStatus;
import com.booking.commondb.repository.GeneratorRepository;
import com.booking.feignclients.clients.RegistratorClient;
import com.booking.feignclients.dto.BookingRequest;
import com.booking.feignclients.dto.BookingResponse;
import com.booking.generator.mapper.BookingRequestMapper;
import com.booking.generator.mapper.GeneratedEntityMapper;
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
    private final GeneratedEntityMapper generatedEntityMapper;
    private final BookingRequestMapper bookingRequestMapper;

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
            BookingRequestDb dbDto = generatedEntityMapper.toDb(entity);

            BookingRequest feignDto = bookingRequestMapper.toFeign(dbDto);

            BookingResponse response = registratorClient.sendBooking(feignDto);

            entity.setStatus(response.success()
                    ? GeneratedEntityStatus.BOOKED
                    : GeneratedEntityStatus.DENIED);

        } catch (Exception ex) {
            entity.setStatus(GeneratedEntityStatus.FAILED);
        }

        entity.setLastModifiedAt(Instant.now());
        generatorRepository.save(entity);
    }

    private String generateRandomBooker() {
        int num = ThreadLocalRandom.current().nextInt(1000, 9999);
        return "Client" + num;
    }

}
