package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.entity.Review;
import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.model.response.VerifyResponse;
import com.example.booking_movie_ticket.security.CustomUserDetails;
import com.example.booking_movie_ticket.service.AuthService;
import com.example.booking_movie_ticket.service.WebService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
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
        return "web/lich-chieu";
    }
    @GetMapping("/thong-tin-ca-nhan")
    public String getProfilePage(Model model) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=customUserDetails.getUser();
        model.addAttribute("user",user);
        return "web/thong-tin-ca-nhan";
    }
    @GetMapping("/gia-ve")
    public String getPrice(Model model) {
        return "web/gia-ve";
    }
}
