package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ReturnUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toUserFromCreateDto(CreateUserDto userDto);

    User toUserFromUpdateDto(UpdateUserDto userDto);

    User toUserFromReturnUserDto(ReturnUserDto userDto);

    CreateUserDto toCreateUserDto(User user);

    UpdateUserDto toUpdateUserDto(User user);

    ReturnUserDto toReturnUserDto(User user);
}