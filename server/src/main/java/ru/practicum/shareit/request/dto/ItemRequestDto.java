package ru.practicum.shareit.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.dto.ItemDtoForRequest;
import ru.practicum.shareit.user.dto.ReturnUserDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ItemRequestDto {
    private Long id;
    private String description;
    private ReturnUserDto requestor;
    private LocalDateTime created;
    private List<ItemDtoForRequest> items;
}