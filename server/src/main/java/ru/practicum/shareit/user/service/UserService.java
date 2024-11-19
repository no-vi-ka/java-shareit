package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.EmailAlreadyInUseException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ReturnUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ReturnUserDto createUser(CreateUserDto dtoUser) {
        User createdUser = userMapper.toUserFromCreateDto(dtoUser);
        emailValidation(createdUser);
        log.info("User создан.");
        return userMapper.toReturnUserDto(userRepository.save(createdUser));
    }

    public ReturnUserDto updateUser(Long id, UpdateUserDto dtoUser) {
        User userFromTable = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User with id = " + id + " not found."));
        User userFromDto = userMapper.toUserFromUpdateDto(dtoUser);
        if (userFromDto.getEmail() != null) {
            emailValidation(userFromDto);
            userFromTable.setEmail(userFromDto.getEmail());
        }
        if (userFromDto.getName() != null) {
            userFromTable.setName(userFromDto.getName());
        }
        log.info("User с id = " + id + " обновлен.");
        return userMapper.toReturnUserDto(userRepository.save(userFromTable));
    }

    public ReturnUserDto getUserById(Long id) {
        return userMapper.toReturnUserDto(userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User with id = " + id + " not found.")));
    }

    public List<ReturnUserDto> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toReturnUserDto).toList();
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id = " + id + " not found.");
        }
        log.info("User с id = " + id + " удален.");
        userRepository.deleteById(id);
    }

    private void emailValidation(User user) {
        if (user.getEmail() == null) {
            throw new ValidationException("Email must be not null.");
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Email must contains @ symbol.");
        }
        if (userRepository.findAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new EmailAlreadyInUseException("Email " + user.getEmail() + " already in use.");
        }
    }
}