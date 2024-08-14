package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.entity.Country;
import com.example.booking_movie_ticket.entity.Genre;
import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.entity.Review;
import com.example.booking_movie_ticket.repository.CountryRepository;
import com.example.booking_movie_ticket.repository.GenreRepository;
import com.example.booking_movie_ticket.repository.MovieRepository;
import com.example.booking_movie_ticket.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebService {
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;

    public List<Movie> getHotMovie(){
        return movieRepository.findTop6ByStatusOrderByRatingDesc(true);
    }
    public List<Movie>getAllMovies(){
        return movieRepository.findAll(Sort.by(("createdAt")).descending());
    }
    public Movie getMovieDetail(Integer id,String slug){
        return movieRepository.findByStatusAndIdAndSlug(true,id,slug).orElse(null);
    }
    public List<Movie>getRelateMovies(Movie movie){
        List<Genre>genres=movie.getGenres()==null?new ArrayList<>() : movie.getGenres();
        List<Integer>genreIds=genres.stream().map(genre -> genre.getId()).toList();
        return movieRepository.findTop6ByGenres_IdInAndStatusAndIdNotOrderByRatingDesc(genreIds ,true, movie.getId());
    }
    public List<Review>getReviews(Movie movie){
        return reviewRepository.findAllByMovie_Id(movie.getId());
    }
    public List<Movie>getMovieByCountry(Integer countryId){
        return movieRepository.findTop6ByCountry_IdAndStatus(countryId,true);
    }
    public List<Country>getAllCountries(){
        return countryRepository.findAll();
    }
    public Country findByCountrySlug(String slug) {
        return countryRepository.findBySlug(slug);
    }
    public Genre findByGenreSlug(String slug) {
        return genreRepository.findBySlug(slug);
    }
    public List<Genre>getAllGenres(){
        return genreRepository.findAll();
    }
    public List<Movie>getMovieByGenre(Integer genreId){
        return movieRepository.findTop6ByCountry_IdAndStatus(genreId,true);
    }
}
