package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.Status;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ReturnBookingDto {
    @NotNull(message = "id .")
    @Positive(message = "id .")
    private Integer id;
    @NotNull(message = "start .")
    @FutureOrPresent(message = "start .")
    private LocalDateTime start;
    @NotNull(message = "end .")
    @Future(message = "end .")
    private LocalDateTime end;
    @NotNull(message = "item .")
    @NotBlank(message = "item .")
    private Item item;
    @NotNull(message = "booker .")
    @NotBlank(message = "booker .")
    private User booker;
    @NotNull(message = "status .")
    @NotBlank(message = "status .")
    private Status status;
}
