package com.example.booking_movie_ticket;

import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.model.enums.Role;
import com.example.booking_movie_ticket.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class BookingMovieTicketApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void save_users(){
        Role userRole=Role.User;
        Role adminRole=Role.Admin;

        User user1= User.builder()
                .name("khue")
                .email("khue@gmail.com")
                .password(passwordEncoder.encode("123"))
                .role(adminRole)
                .build();
        userRepository.save(user1);

        User user2= User.builder()
                .name("khue")
                .email("khue1@gmail.com")
                .password(passwordEncoder.encode("123"))
                .role(userRole)
                .build();
        userRepository.save(user2);
    }

}
