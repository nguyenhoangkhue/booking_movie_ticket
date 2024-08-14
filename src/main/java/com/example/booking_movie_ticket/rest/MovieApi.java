package com.example.booking_movie_ticket.rest;

import com.example.booking_movie_ticket.model.request.CreateMovieRequest;
import com.example.booking_movie_ticket.model.request.UpdateMovieRequest;
import com.example.booking_movie_ticket.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/admin/movies")
@RequiredArgsConstructor
public class MovieApi {
    private final MovieService movieService;
    @PostMapping
    ResponseEntity<?> createMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest){
        return ResponseEntity.ok(movieService.createMovie(createMovieRequest));
    }
    @PutMapping("/{id}/update")
    ResponseEntity<?> updateMovie(@Valid @RequestBody UpdateMovieRequest updateMovieRequest,
                                  @PathVariable Integer id){
        return ResponseEntity.ok(movieService.updateMovie(updateMovieRequest,id));
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteMovie(@PathVariable Integer id){
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/upload-poster")
    ResponseEntity<?> uploadPoster(@PathVariable Integer id, @RequestParam MultipartFile file) {
        return ResponseEntity.ok(movieService.uploadPoster(id, file));
    }
}
