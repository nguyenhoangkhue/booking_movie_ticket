package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Country, Integer> {
}
