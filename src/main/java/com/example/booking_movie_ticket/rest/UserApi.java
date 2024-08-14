package com.example.booking_movie_ticket.rest;

import com.example.booking_movie_ticket.model.request.AdminUpdateProfileRequest;
import com.example.booking_movie_ticket.model.request.CreateUserRequest;
import com.example.booking_movie_ticket.model.request.UpdatePasswordRequest;
import com.example.booking_movie_ticket.model.request.UpdateProfileRequest;
import com.example.booking_movie_ticket.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    @PutMapping("/users/update-profile")
    ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request) {
        userService.updateProfile(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/update-password")
    ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/admin/users")
    ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request){
        return ResponseEntity.ok(userService.createUser(request));
    }
    @PutMapping("/admin/users/{id}")
    ResponseEntity<?> adminUpdateProfile( @Valid @RequestBody AdminUpdateProfileRequest request,
                                          @PathVariable Integer id) {
        userService.adminUpdateProfile(request,id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/admin/users/{id}/reset-password")
    ResponseEntity<?> resetPassword(@Valid @RequestBody
                                    @PathVariable Integer id) {
        userService.resetPassword(id);
        return ResponseEntity.ok().build();
    }
}
