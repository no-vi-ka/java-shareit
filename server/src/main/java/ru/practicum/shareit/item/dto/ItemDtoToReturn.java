package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.user.dto.ReturnUserDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDtoToReturn {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private ReturnUserDto owner;
    private Long requestId;
}