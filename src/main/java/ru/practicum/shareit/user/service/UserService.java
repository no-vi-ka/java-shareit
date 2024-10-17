package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.createUserDto;
import ru.practicum.shareit.user.dto.updateUserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User createUser(createUserDto dtoUser) {
        return userRepository.createUser(dtoUser);
    }

    public User updateUser(Integer id, updateUserDto dtoUser) {
        return userRepository.updateUser(id, dtoUser);
    }

    public User getUserById(Integer id) {
        return userRepository.getUserById(id);
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public void deleteUser(Integer id) {
        userRepository.deleteUser(id);
    }
}