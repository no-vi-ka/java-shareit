package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateItemDto {
    @NotNull(message = "name должно быть указано.")
    @NotBlank(message = "name не должно быть пустым.")
    private String name;
    @NotNull(message = "description должен быть указан.")
    @NotBlank(message = "description не должен быть пустым.")
    private String description;
    @NotNull(message = "Статус available должен быть указан.")
    private Boolean available;
}