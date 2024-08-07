package com.example.booking_movie_ticket.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "halls")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String name;

    Integer totalRows;
    Integer totalCols;

    public Integer getTotalSeats() {
        return totalRows * totalCols;
    }

    Integer capacity;
    Date createdAt;
    Date updatedAt;
}
