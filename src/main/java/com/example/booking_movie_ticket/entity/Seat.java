package com.example.booking_movie_ticket.entity;

import com.example.booking_movie_ticket.model.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "seats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    SeatType seatType;

    Boolean status;
    Integer rowIndex;
    Integer colIndex;
    String seatPosition;

    @ManyToOne
    @JoinColumn(name="hall_id")
    Hall hall;

    Date createdAt;
    Date updatedAt;
}
