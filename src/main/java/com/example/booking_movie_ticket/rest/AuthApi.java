package com.example.booking_movie_ticket.rest;

import com.example.booking_movie_ticket.model.request.LoginRequest;
import com.example.booking_movie_ticket.model.request.SignupRequest;
import com.example.booking_movie_ticket.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApi {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        authService.login(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request){
        authService.signup(request);
        return ResponseEntity.ok().build();
    }
}
