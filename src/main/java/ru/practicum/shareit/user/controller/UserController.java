package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody @Valid CreateUserDto dtoUser) {
        log.info("Пришёл запрос на создание пользователя с email: {}.", dtoUser.getEmail());
        return userService.createUser(dtoUser);
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody UpdateUserDto dtoUser) {
        log.info("Пришёл запрос на обновление пользователя с email: {}.", dtoUser.getEmail());
        return userService.updateUser(id, dtoUser);
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Пришёл запрос на получение списка всех пользователей.");
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        log.info("Пришёл запрос на получение пользователя с id: {}.", id);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{user-id}")
    public void deleteUser(@PathVariable("user-id") Integer id) {
        log.info("Пришёл запрос на удаление пользователя с id: {}.", id);
        userService.deleteUser(id);
    }
}