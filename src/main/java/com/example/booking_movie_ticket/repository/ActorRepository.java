package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
}
