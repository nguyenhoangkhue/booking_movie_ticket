package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.repository.ActorRepository;
import com.example.booking_movie_ticket.repository.CountryRepository;
import com.example.booking_movie_ticket.repository.DirectorRepository;
import com.example.booking_movie_ticket.repository.GenreRepository;
import com.example.booking_movie_ticket.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    private final CountryRepository countryRepository;
    @GetMapping("/admin/movies")
    public String movies(Model model) {
        List<Movie> movies=movieService.getAllMovies();
        model.addAttribute("movies",movies);
        return "admin/movie/index";
    }

    @GetMapping("/admin/movies/create")
    public String create(Model model) {
        model.addAttribute("directors",directorRepository.findAll());
        model.addAttribute("actors",actorRepository.findAll());
        model.addAttribute("genres",genreRepository.findAll());
        model.addAttribute("countries",countryRepository.findAll());
        return "admin/movie/create";
    }

    @GetMapping("/admin/movies/{id}/details")
    public String detail(@PathVariable Integer id,
                         Model model) {
        model.addAttribute("directors",directorRepository.findAll());
        model.addAttribute("actors",actorRepository.findAll());
        model.addAttribute("genres",genreRepository.findAll());
        model.addAttribute("countries",countryRepository.findAll());
        Movie movie=movieService.getMovieDetail(id);
        model.addAttribute("movie",movie);
        return "admin/movie/detail";
    }

}

