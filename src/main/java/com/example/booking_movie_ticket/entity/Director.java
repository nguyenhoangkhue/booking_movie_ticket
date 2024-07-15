package com.example.booking_movie_ticket.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "directors")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String name;

    String slug;
    String avatar;

    @Column(columnDefinition = "TEXT")
    String bio;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}