package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    public User getUserDetail(Integer id){
        return userRepository.findUserById(id).orElse(null);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll(Sort.by("createdAt").descending());
    }
}