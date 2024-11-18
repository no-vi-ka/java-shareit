package ru.practicum.shareit.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnUserDto {
    private Long id;
    private String name;
    private String email;
}