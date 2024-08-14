package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
    Optional<Actor> findActorById(Integer id);
}
