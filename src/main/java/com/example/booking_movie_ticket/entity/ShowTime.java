package com.example.booking_movie_ticket.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "showtimes")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;

    @ManyToOne
    @JoinColumn(name = "hall_id")
    Hall hall;

    LocalDate date;
    String startTime;
    String endTime;
    Date createdAt;
    Date updatedAt;
}
