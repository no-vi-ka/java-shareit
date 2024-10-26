package ru.practicum.shareit.request.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ItemRequest {
    @Positive(message = "Значение id должно быть положительным.")
    private Integer id;
    @NotNull(message = "description должен быть указан.")
    @NotBlank(message = "description не должен быть пустым.")
    private String description;
    @NotNull(message = "requestor должен быть указан.")
    @NotBlank(message = "requestor не должен быть пустым.")
    private User requestor;
    @PastOrPresent(message = "created не может быть в будущем.")
    LocalDateTime created;
}