package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Integer> {
}
