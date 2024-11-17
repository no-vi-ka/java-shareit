package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookingDto {
    @FutureOrPresent(message = "start не должен быть в прошлом.")
    private LocalDateTime start;
    @Future(message = "end должен быть в будущем.")
    private LocalDateTime end;
    private Long itemId;
}