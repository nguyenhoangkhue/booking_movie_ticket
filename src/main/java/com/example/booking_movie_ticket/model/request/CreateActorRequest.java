package com.example.booking_movie_ticket.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateActorRequest {
    @NotEmpty(message = "Tên phim không được để trống")
    String name;
    @NotEmpty(message = "Tiểu sử không được để trống")
    String bio;
}
