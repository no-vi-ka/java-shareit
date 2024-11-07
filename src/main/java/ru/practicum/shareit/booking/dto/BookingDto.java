package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BookingDto {
    @FutureOrPresent
    private LocalDateTime start;
    @Future
    private LocalDateTime end;
    private Integer itemId;
}