package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.model.ItemRequest;


@Data
@Builder
@AllArgsConstructor
public class Item {
    @Positive(message = "Значение id должно быть положительным.")
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
    private Integer owner;
    private ItemRequest request;
}