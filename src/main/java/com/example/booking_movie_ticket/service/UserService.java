package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.exception.BadRequestException;
import com.example.booking_movie_ticket.model.request.UpdatePasswordRequest;
import com.example.booking_movie_ticket.model.request.UpdateProfileRequest;
import com.example.booking_movie_ticket.repository.UserRepository;
import com.example.booking_movie_ticket.security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final HttpSession session;
    private final PasswordEncoder passwordEncoder;

    public void updateProfile(UpdateProfileRequest request) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=customUserDetails.getUser();

        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void updatePassword(UpdatePasswordRequest request) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=customUserDetails.getUser();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Mật khẩu cũ không đúng");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Mật khẩu xác nhận không khớp");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        session.setAttribute("currentUser", user);
        userRepository.save(user);
    }
}