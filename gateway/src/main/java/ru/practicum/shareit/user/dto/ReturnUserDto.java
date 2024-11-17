package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReturnUserDto {
    private @NotNull(message = "id должен быть указан.")
    @Positive(message = "id должен быть положительным.") Long id;
    @NotNull(message = "name должно быть указано.")
    @NotBlank(message = "name не должен быть пустым.")
    private String name;
    @NotNull(message = "email должен быть указан.")
    @NotBlank(message = "email не должен быть пустым.")
    @Email(message = "email должен быть указан корректно.")
    private String email;
}