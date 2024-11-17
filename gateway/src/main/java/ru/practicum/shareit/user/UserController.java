package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserDto dtoUser) {
        log.info("Пришёл запрос на создание пользователя с email: {}.", dtoUser.getEmail());
        return userClient.createUser(dtoUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto dtoUser) {
        log.info("Пришёл запрос на обновление пользователя с email: {}.", dtoUser.getEmail());
        return userClient.updateUser(id, dtoUser);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        log.info("Пришёл запрос на получение списка всех пользователей.");
        return userClient.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        log.info("Пришёл запрос на получение пользователя с id: {}.", id);
        return userClient.getUserById(id);
    }

    @DeleteMapping("/{user-id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("user-id") Long id) {
        log.info("Пришёл запрос на удаление пользователя с id: {}.", id);
        return userClient.deleteUser(id);
    }
}
