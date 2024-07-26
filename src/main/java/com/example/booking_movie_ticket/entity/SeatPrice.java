package com.example.booking_movie_ticket.entity;

import com.example.booking_movie_ticket.model.enums.DayType;
import com.example.booking_movie_ticket.model.enums.SeatType;
import com.example.booking_movie_ticket.model.enums.Time;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "seat_price")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Enumerated(EnumType.STRING)
    SeatType seatType;

    @Enumerated(EnumType.STRING)
    DayType dayType;

    @Enumerated(EnumType.STRING)
    Time time;

    Integer price;

}
