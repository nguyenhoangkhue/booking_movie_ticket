package com.example.booking_movie_ticket.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReviewRequest {
    @NotEmpty(message = "Nội dung không được để trống")
    String content;
    @NotEmpty(message = "Đánh giá không được để trống")
    Integer rating;
    @NotEmpty(message = "Id phim không được để trống")
    Integer movieId;
}
