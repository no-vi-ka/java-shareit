package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.ItemDtoToReturn;
import ru.practicum.shareit.status.Status;
import ru.practicum.shareit.user.dto.ReturnUserDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReturnBookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private ItemDtoToReturn item;
    private ReturnUserDto booker;
    private Status status;
}
