package com.booking.commondb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {

    @Id
    @UuidGenerator
    private UUID uid;

    private String person;
    private Double amount;
    private Instant processedAt;
}
