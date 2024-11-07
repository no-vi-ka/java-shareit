package ru.practicum.shareit.booking.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.Status;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnBookingDto {
    @NotNull(message = "id должен быть указан.")
    @Positive(message = "id должен быть положительным.")
    private Integer id;
    @NotNull(message = "start должен быть указан.")
    @FutureOrPresent(message = "start не должен быть в прошлом.")
    private LocalDateTime start;
    @NotNull(message = "end должен быть указан.")
    @Future(message = "end должен быть в будущем.")
    private LocalDateTime end;
    @NotNull(message = "item должен быть указан.")
    @NotBlank(message = "item не должен быть в прошлом.")
    private Item item;
    @NotNull(message = "booker должен быть указан.")
    @NotBlank(message = "booker не должен быть в прошлом.")
    private User booker;
    @NotNull(message = "status должен быть указан.")
    @NotBlank(message = "status не должен быть в прошлом.")
    private Status status;
}