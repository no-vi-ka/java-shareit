package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateItemRequestDto {
    @NotNull(message = "description должен быть указан.")
    @NotBlank(message = "description не должен быть пустым.")
    private String description;
    private Long userId;
}
