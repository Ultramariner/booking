package com.booking.commondb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "apartment_id", referencedColumnName = "id")
    private Apartment apartment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resident_id", referencedColumnName = "id")
    private Resident resident;

    private Instant bookedAt;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    private UUID paymentUid;
}
