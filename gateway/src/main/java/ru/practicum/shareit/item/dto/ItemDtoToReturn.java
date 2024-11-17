package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.shareit.user.dto.ReturnUserDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ItemDtoToReturn {
    private Long id;
    @NotNull(message = "name должно быть указано.")
    @NotBlank(message = "name не должно быть пустым.")
    private String name;
    @NotNull(message = "description должен быть указан.")
    @NotBlank(message = "description не должен быть пустым.")
    private String description;
    @NotNull(message = "Статус available должен быть указан.")
    private Boolean available;
    @NotNull(message = "owner должен быть указан.")
    @NotBlank(message = "owner не должен быть пустым.")
    private ReturnUserDto owner;
    private Long requestId;
}