package com.example.booking_movie_ticket.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieRequest {
    @NotEmpty(message = "Tên phim không được để trống")
    String name;
    @NotEmpty(message = "Trailer phim không được để trống")
    String trailerURL;
    @NotEmpty(message = "Mô tả không được để trống")
    String description;

    List<Integer>genreIds;
    List<Integer>actorIds;
    List<Integer>directorIds;

    @NotNull(message = "Năm phát hành không được để trống")
    Integer releaseYear;
    @NotNull(message = "Trạng thái không được để trống")
    Boolean status;
    @NotNull(message = "Quốc gia không được để trống")
    Integer CountryId;
}
