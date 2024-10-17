package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.user.dto.createUserDto;
import ru.practicum.shareit.user.dto.updateUserDto;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserFromCreateDto(createUserDto userDto);

    User toUserFromUpdateDto(updateUserDto userDto);

    createUserDto toCreateUserDto(User user);

    updateUserDto toUpdateUserDto(User user);
}