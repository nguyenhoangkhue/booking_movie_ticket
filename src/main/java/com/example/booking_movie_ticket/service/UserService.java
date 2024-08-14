package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.exception.BadRequestException;
import com.example.booking_movie_ticket.exception.ResourceNotFoundException;
import com.example.booking_movie_ticket.model.request.AdminUpdateProfileRequest;
import com.example.booking_movie_ticket.model.request.CreateUserRequest;
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
import java.util.Optional;

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
    public User createUser(CreateUserRequest request){
        Optional<User> userOptional=userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()){
            throw new BadRequestException("Email đã tồn tại");
        }
        User user=User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .avatar("https://placehold.co/600x400?text=" + request.getName())
                .role(request.getRole())
                .password(passwordEncoder.encode("123"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }
    public void adminUpdateProfile(AdminUpdateProfileRequest request, Integer id) {
        User user = userRepository.findUserById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        user.setName(request.getName());

        userRepository.save(user);
    }
    public void resetPassword(Integer id) {
        User user = userRepository.findUserById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode("123"));

        userRepository.save(user);
    }
}