package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.dto.ReturnUserDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ItemDtoForRequest {
    private Long id;
    private String name;
    private ReturnUserDto owner;
}
