package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

@Getter
@Setter
@AllArgsConstructor
public class ReturnItemDto {
    @NotNull(message = "id должен быть указан.")
    @Positive(message = "id должен быть положительным.")
    private Integer id;
    @NotNull(message = "name должен быть указан.")
    @NotBlank(message = "name не должен быть пустым.")
    private String name;
    @NotNull(message = "description должен быть указан.")
    @NotBlank(message = "description не должен быть пустым.")
    private String description;
    @NotNull(message = "available должен быть указан.")
    private Boolean available;
    @NotNull(message = "owner должен быть указан.")
    @NotBlank(message = "owner не должен быть пустым.")
    private User owner;
    private ItemRequest request;
}
