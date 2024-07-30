package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.SeatReservation;
import com.example.booking_movie_ticket.model.enums.SeatReservationStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatReservationRepository {
    List<SeatReservation> findByStatusAndStartTimeBefore(SeatReservationStatus seatReservationStatus, LocalDateTime localDateTime);
}
