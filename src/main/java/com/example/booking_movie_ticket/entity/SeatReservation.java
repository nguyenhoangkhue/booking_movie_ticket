package com.example.booking_movie_ticket.entity;

import com.example.booking_movie_ticket.model.enums.SeatReservationStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "seat_reservations")
public class SeatReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    private Seat seat;

    @ManyToOne
    private ShowTime showtime;

    @Enumerated(EnumType.STRING)
    private SeatReservationStatus status;

    private LocalDateTime startTime;

    @PrePersist
    protected void onCreate() {
        startTime = LocalDateTime.now();
    }
}