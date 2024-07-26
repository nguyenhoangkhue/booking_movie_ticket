package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.entity.Review;
import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.exception.BadRequestException;
import com.example.booking_movie_ticket.exception.ResourceNotFoundException;
import com.example.booking_movie_ticket.model.request.CreateReviewRequest;
import com.example.booking_movie_ticket.model.request.UpdateReviewRequest;
import com.example.booking_movie_ticket.repository.MovieRepository;
import com.example.booking_movie_ticket.repository.ReviewRepository;
import com.example.booking_movie_ticket.repository.UserRepository;
import com.example.booking_movie_ticket.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    public List<Review>getReviewsByMovie(Integer id){
        return reviewRepository.findAllByMovie_IdOrderByCreatedAtDesc(id);
    }
    //TODO:Validation huong dan sau(StringBoot Validation)
    public Review createReview(CreateReviewRequest request) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=customUserDetails.getUser();
        Movie movie=movieRepository.findById(request.getMovieId())
                .orElseThrow(()->new ResourceNotFoundException("Movie not found"));
        Review review=Review.builder()
                .content(request.getContent())
                .rating(Double.valueOf(request.getRating()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .movie(movie)
                .build();
        reviewRepository.save(review);
        return review;
    }

    public Review updateReview(Integer id, UpdateReviewRequest request) {
        Review review=reviewRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Review not found"));
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=customUserDetails.getUser();
        if (!review.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You can't update this review");
        }
        review.setContent(request.getContent());
        review.setRating(Double.valueOf(request.getRating()));
        review.setUpdatedAt(LocalDateTime.now());
        reviewRepository.save(review);
        return review;
    }

    public void deleteReview(Integer id) {
        Review review=reviewRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Review not found"));
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=customUserDetails.getUser();
        if (!review.getUser().getId().equals(user.getId())){
            throw new BadRequestException("You can't update this review");
        }
        reviewRepository.deleteById(id);
    }
}
