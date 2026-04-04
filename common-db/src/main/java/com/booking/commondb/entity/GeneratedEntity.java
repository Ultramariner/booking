package com.booking.commondb.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "generated_entity", schema = "generator_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String booker;
    private Instant createdAt;
    private Instant lastModifiedAt;

    @Enumerated(EnumType.STRING)
    private GeneratedEntityStatus status;
}
