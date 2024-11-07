package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateUserDto {
    @NotNull(message = "name должно быть указано.")
    @NotBlank(message = "name не должен быть пустым.")
    private String name;
    @NotNull(message = "email должен быть указан.")
    @NotBlank(message = "email не должен быть пустым.")
    @Email(message = "email должен быть указан корректно.")
    private String email;
}