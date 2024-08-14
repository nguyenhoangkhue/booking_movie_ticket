package com.example.booking_movie_ticket.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminUpdateProfileRequest {
    @NotEmpty(message="Tên không được để trống")
    String name;
    @NotEmpty(message = "Email không được để trống")
    @Email(message="Email không đúng định dạng")
    String email;
}
