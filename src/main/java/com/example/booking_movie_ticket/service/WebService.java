package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.entity.Review;
import com.example.booking_movie_ticket.repository.MovieRepository;
import com.example.booking_movie_ticket.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class WebService {
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    public List<Movie> getHotMovie(){
        return movieRepository.findTop10ByStatusOrderByRatingDesc(true);
    }
    public List<Movie>getAllMovies(){
        return movieRepository.findAll(Sort.by(("createdAt")).descending());
    }
    public Movie getMovieDetail(Integer id,String slug){
        return movieRepository.findByStatusAndIdAndSlug(true,id,slug).orElse(null);
    }
    public List<Movie>getRelateMovies(Movie movie){
        return movieRepository.findTop6ByGenresAndStatusAndIdNotOrderByRatingDesc(movie.getGenres() ,true, movie.getId());
    }
    public List<Review>getReviews(Movie movie){
        return reviewRepository.findAllByMovie_Id(movie.getId());
    }
}
