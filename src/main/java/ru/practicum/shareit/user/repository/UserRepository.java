package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.EmailAlreadyInUseException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final UserMapper userMapper;

    private Map<Integer, User> users = new HashMap<>();
    private Set<String> emails = new HashSet<>();
    private Integer id = 0;

    public User createUser(CreateUserDto dtoUser) {
        User user = userMapper.toUserFromCreateDto(dtoUser);
        for (User u : users.values()) {
            if (u.getEmail().equals(user.getEmail())) {
                throw new EmailAlreadyInUseException("Email " + user.getEmail() + " уже используется.");
            }
        }
        user.setId(++id);
        users.put(id, user);
        emails.add(user.getEmail());
        log.info("Создан пользователь с id: {}.", id);
        return user;
    }

    public User updateUser(Integer id, UpdateUserDto dtoUser) {
        User user = getUserById(id);
        checkEmailAlreadyInUse(dtoUser.getEmail(), id);
        if (dtoUser.getName() != null) {
            user.setName(dtoUser.getName());
        }
        if (dtoUser.getEmail() != null) {
            emails.remove(user.getEmail());
            user.setEmail(dtoUser.getEmail());
            emails.add(user.getEmail());
        }
        users.put(id, user);
        log.info("Обновлен пользователь с id: {}.", id);
        return user;
    }

    public List<User> getUsers() {
        log.info("Получен список всех пользователей.");
        return users.values().stream().toList();
    }

    public User getUserById(Integer userId) {
        log.info("Получен пользователь с id: {}.", userId);
        return Optional.ofNullable(users.get(userId)).orElseThrow(() -> new NotFoundException("Пользователь с id = " + id + " не найден."));
    }

    public void deleteUser(Integer id) {
        checkContainsUserById(id);
        users.remove(id);
        log.info("Удален пользователь с id: {}.", id);
    }

    public void checkContainsUserById(Integer id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь с id = " + id + " не найден.");
        }
    }

    private void checkEmailAlreadyInUse(String email, Integer id) {
        if (emails.contains(email) && (!users.get(id).getEmail().equals(email))) {
            throw new EmailAlreadyInUseException("Email " + email + " уже используется");
        }
    }
}