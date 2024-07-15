package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Genre;
import com.example.booking_movie_ticket.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findTop10ByStatusOrderByRatingDesc(Boolean status);

    List<Movie> findTop6ByGenresAndStatusAndIdNotOrderByRatingDesc(List<Genre> genre,Boolean status,Integer id);

    Optional<Movie> findByStatusAndIdAndSlug(Boolean status,Integer id,String slug);
}
