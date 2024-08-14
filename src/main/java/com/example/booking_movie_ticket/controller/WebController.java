package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.entity.*;
import com.example.booking_movie_ticket.model.response.VerifyResponse;
import com.example.booking_movie_ticket.repository.CountryRepository;
import com.example.booking_movie_ticket.security.CustomUserDetails;
import com.example.booking_movie_ticket.service.AuthService;
import com.example.booking_movie_ticket.service.WebService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final WebService webService;
    private final AuthService authService;

    @GetMapping("")
    public String getHomePage(Model model){
        List<Movie> listHot=webService.getHotMovie();
        List<Movie> listMovie=webService.getAllMovies();
        model.addAttribute("listMovie",listMovie);
        model.addAttribute("listHot",listHot);
        List<Genre> genres=webService.getAllGenres();
        model.addAttribute("genres", genres);
        List<Country> countries=webService.getAllCountries();
        model.addAttribute("countries", countries);
        return "web/index";
    }
    @GetMapping("/phim/{id}/{slug}")
    public String getMovieDetail(@PathVariable Integer id,
                                 @PathVariable String slug,
                                 Model model) {
        Movie movie = webService.getMovieDetail(id, slug);
        List<Movie> relateMovies = webService.getRelateMovies(movie);
        List<Review> listReviews = webService.getReviews(movie);
        model.addAttribute("movie", movie);
        model.addAttribute("relateMovies", relateMovies);
        model.addAttribute("listReviews", listReviews);
        List<Genre> genres=webService.getAllGenres();
        model.addAttribute("genres", genres);
        List<Country> countries=webService.getAllCountries();
        model.addAttribute("countries", countries);
        return "web/chi-tiet-phim";
    }
    @GetMapping("/signin")
    public String getSignin(){
        return "web/signin";
    }
    @GetMapping("/register")
    public String getRegister() {
        return "web/register";
    }
    @GetMapping("/xac-thuc-tai-khoan")
    public String getVerifyAccount(@RequestParam String token, Model model){
        VerifyResponse response=authService.verifyAccount(token);
        model.addAttribute("response",response);
        return "web/verify-account";
    }
    @GetMapping("/lich-chieu")
    public String getSchedule(Model model){
        List<Movie> listMovie=webService.getAllMovies();
        model.addAttribute("listMovie",listMovie);
        List<Genre> genres=webService.getAllGenres();
        model.addAttribute("genres", genres);
        List<Country> countries=webService.getAllCountries();
        model.addAttribute("countries", countries);
        return "web/lich-chieu";
    }
    @GetMapping("/thong-tin-ca-nhan")
    public String getProfilePage(Model model) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=customUserDetails.getUser();
        model.addAttribute("user",user);
        List<Genre> genres=webService.getAllGenres();
        model.addAttribute("genres", genres);
        List<Country> countries=webService.getAllCountries();
        model.addAttribute("countries", countries);
        return "web/thong-tin-ca-nhan";
    }
    @GetMapping("/gia-ve")
    public String getPrice(Model model) {
        return "web/gia-ve";
    }
    @GetMapping("/movies/{slug}")
    public String getCountries(@PathVariable String slug, Model model) {
        Country country = webService.findByCountrySlug(slug);
        List<Country> countries=webService.getAllCountries();
        model.addAttribute("countryName", country.getName());
        model.addAttribute("countrySlug", country.getSlug());
        model.addAttribute("countries", countries);
        List<Movie> movies=webService.getMovieByCountry(country.getId());
        model.addAttribute("movies",movies);
        List<Genre> genres=webService.getAllGenres();
        model.addAttribute("genres", genres);
        return "web/countries";
    }
    @GetMapping("/genres/{slug}")
    public String getGenres(@PathVariable String slug, Model model) {
        Genre genre = webService.findByGenreSlug(slug);
        List<Genre> genres=webService.getAllGenres();
        model.addAttribute("genreName", genre.getName());
        model.addAttribute("genreSlug", genre.getSlug());
        model.addAttribute("genres", genres);
        List<Movie> movies=webService.getMovieByGenre(genre.getId());
        model.addAttribute("movies",movies);
        return "web/genres";
    }
}
