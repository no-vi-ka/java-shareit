package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserFromCreateDto(CreateUserDto userDto);

    User toUserFromUpdateDto(UpdateUserDto userDto);

    CreateUserDto toCreateUserDto(User user);

    UpdateUserDto toUpdateUserDto(User user);
}