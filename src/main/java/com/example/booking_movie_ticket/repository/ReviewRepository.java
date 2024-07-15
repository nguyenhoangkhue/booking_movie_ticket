package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByMovie_Id(Integer id);

    List<Review> findAllByMovie_IdOrderByCreatedAtDesc(Integer id);
}
