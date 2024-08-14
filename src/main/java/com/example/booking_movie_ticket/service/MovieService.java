package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.entity.Country;
import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.exception.ResourceNotFoundException;
import com.example.booking_movie_ticket.model.request.CreateMovieRequest;
import com.example.booking_movie_ticket.model.request.UpdateMovieRequest;
import com.example.booking_movie_ticket.repository.*;
import com.github.slugify.Slugify;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final CloudinaryService cloudinaryService;
    Slugify slugify= Slugify.builder().build();
    public List<Movie>getAllMovies(){
        return movieRepository.findAll(Sort.by(("createdAt")).descending());
    }
    public Movie getMovieDetail(Integer id){
        return movieRepository.findMovieById(id).orElse(null);
    }
    public Movie createMovie(CreateMovieRequest request){
        Country country=countryRepository.findById(request.getCountryId())
                .orElseThrow(()->new ResourceNotFoundException("Quốc gia không tồn tại"));
        Movie movie=Movie.builder()
                .name(request.getName())
                .slug(slugify.slugify(request.getName()))
                .description(request.getDescription())
                .poster("https://placehold.co/600x400?text="+request.getName().substring(0,1).toLowerCase())
                .releaseYear(request.getReleaseYear())
                .trailerURL(request.getTrailerURL())
                .status(request.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .publishedAt(request.getStatus()?LocalDateTime.now():null)
                .country(country)
                .genres(genreRepository.findAllById(request.getGenreIds()))
                .actors(actorRepository.findAllById(request.getActorIds()))
                .directors(directorRepository.findAllById(request.getDirectorIds()))
                .build();
        return movieRepository.save(movie);
    }
    public Movie updateMovie(@Valid UpdateMovieRequest request, Integer id){
        Country country=countryRepository.findById(request.getCountryId())
                .orElseThrow(()->new ResourceNotFoundException("Quốc gia không tồn tại"));
        Movie movie = movieRepository.findMovieById(id).orElseThrow(()->new ResourceNotFoundException("Movie not found"));
        movie.setName(request.getName());
        movie.setTrailerURL(request.getTrailerURL());
        movie.setDescription(request.getDescription());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setStatus(request.getStatus());
        movie.setCountry(country);
        movie.setSlug(slugify.slugify(request.getName()));
        movie.setGenres(genreRepository.findAllById(request.getGenreIds()));
        movie.setDirectors(directorRepository.findAllById(request.getDirectorIds()));
        movie.setActors(actorRepository.findAllById(request.getActorIds()));
        movie.setUpdatedAt(LocalDateTime.now());
        movie.setPublishedAt(request.getStatus()?LocalDateTime.now():null);

        return movieRepository.save(movie);
    }
    public void deleteMovie(Integer id) {
        Movie movie=movieRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Movie not found"));
        movieRepository.deleteById(id);
    }
    public String uploadPoster(Integer id, MultipartFile file){
        Movie movie=movieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Không tìm thấy phim"));
        try{
            Map result=cloudinaryService.uploadFile(file);
            System.out.println(result);
            movie.setPoster((String) result.get("url"));
            return (String) result.get("url");
        }catch (Exception e){
            throw new RuntimeException("Lỗi khi upload Poster");
        }
    }
}

