package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class updateUserDto {
    private String name;
    @Email(message = "email должен быть указан корректно.")
    private String email;
}