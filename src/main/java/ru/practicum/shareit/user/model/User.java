package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {
    @Positive(message = "Значение id должно быть положительным.")
    private Integer id;
    @NotNull(message = "name должно быть указано.")
    @NotBlank(message = "name не должно быть пустым.")
    private String name;
    @NotNull(message = "email должен быть указан.")
    @NotBlank(message = "email не должен быть пустым.")
    @Email(message = "email должен быть указан корректно.")
    private String email;
}