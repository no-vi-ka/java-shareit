package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.ItemDtoToReturn;
import ru.practicum.shareit.user.dto.ReturnUserDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReturnBookingDto {
    private @NotNull(message = "id должен быть указан.")
    @Positive(message = "id должен быть положительным.")
    Long id;
    @NotNull(message = "start должен быть указан.")
    @FutureOrPresent(message = "start не должен быть в прошлом.")
    private LocalDateTime start;
    @NotNull(message = "end должен быть указан.")
    @Future(message = "end должен быть в будущем.")
    private LocalDateTime end;
    @NotNull(message = "item должен быть указан.")
    @NotBlank(message = "item не должен быть пустым.")
    private ItemDtoToReturn item;
    @NotNull(message = "booker должен быть указан.")
    @NotBlank(message = "booker не должен быть пустым.")
    private ReturnUserDto booker;
    @NotNull(message = "status должен быть указан.")
    @NotBlank(message = "status не должен быть пустым.")
    private Status status;
}
