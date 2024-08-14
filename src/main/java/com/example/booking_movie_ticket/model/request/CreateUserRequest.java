package com.example.booking_movie_ticket.model.request;

import com.example.booking_movie_ticket.model.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    @NotEmpty(message="Tên không được để trống")
    String name;
    @NotEmpty(message="Email không được để trống")
    @Email(message="Email không hợp lệ")
    String email;
    @Enumerated(EnumType.STRING)
    Role role;
}
