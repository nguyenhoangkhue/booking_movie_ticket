package com.example.booking_movie_ticket.security;

import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email)
                        .orElseThrow(()->new UsernameNotFoundException("User not found"+email));
        return new CustomUserDetails(user);
    }
}