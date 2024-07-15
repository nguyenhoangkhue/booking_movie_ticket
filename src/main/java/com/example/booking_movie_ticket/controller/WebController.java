package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.entity.Review;
import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.service.WebService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final WebService webService;
    private final HttpSession session;
    @GetMapping("")
    public String getHomePage(Model model){
        List<Movie> listHot=webService.getHotMovie();
        List<Movie> listMovie=webService.getAllMovies();
        model.addAttribute("listMovie",listMovie);
        model.addAttribute("listHot",listHot);
        return "web/index";
    }
    @GetMapping("/phim/{id}/{slug}")
    public String getMovieDetail(@PathVariable Integer id,
                                 @PathVariable String slug,
                                 Model model) {
        User user = (User) session.getAttribute("currentUser");
        Movie movie = webService.getMovieDetail(id, slug);
        List<Movie> relateMovies = webService.getRelateMovies(movie);
        List<Review> listReviews = webService.getReviews(movie);
        model.addAttribute("movie", movie);
        model.addAttribute("relateMovies", relateMovies);
        model.addAttribute("listReviews", listReviews);
        return "web/chi-tiet-phim";
    }
    @GetMapping("/signin")
    public String getSignin(){
        return "web/signin";
    }
    @GetMapping("/signup")
    public String getSignup(){
        return "web/signup";
    }
}
