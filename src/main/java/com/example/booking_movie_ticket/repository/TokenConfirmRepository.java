package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.TokenConfirm;
import com.example.booking_movie_ticket.model.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenConfirmRepository extends JpaRepository<TokenConfirm,Integer> {
    Optional<TokenConfirm> findByTokenAndType(String token, TokenType type);
}
