package ru.practicum.shareit.booking.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.Status;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Booking {
    @Positive(message = "Значение id должно быть положительным.")
    private Integer id;
    @NotNull(message = "start должен быть указан.")
    @FutureOrPresent(message = "start не может быть в прошлом.")
    private LocalDateTime start;
    @NotNull(message = "end должен быть указан.")
    @Future(message = "end не может быть в прошлом.")
    private LocalDateTime end;
    @NotNull(message = "item должен быть указан.")
    @NotBlank(message = "item не должен быть пустым.")
    private Item item;
    @NotNull(message = "user должен быть указан.")
    @NotBlank(message = "booker не должен быть пустым.")
    private User booker;
    @NotNull(message = "status должен быть указан.")
    @NotBlank(message = "status не должен быть пустым.")
    private Status status;
}