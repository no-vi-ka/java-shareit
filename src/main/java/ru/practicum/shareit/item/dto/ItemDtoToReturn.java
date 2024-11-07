package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

@Getter
@Setter
@AllArgsConstructor
public class ItemDtoToReturn {
    private Integer id;
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
    private User owner;
    private ItemRequest request;
}