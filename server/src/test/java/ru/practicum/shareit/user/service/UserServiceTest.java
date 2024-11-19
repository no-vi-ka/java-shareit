package ru.practicum.shareit.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.exceptions.EmailAlreadyInUseException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ReturnUserDto;
import ru.practicum.shareit.user.dto.UpdateUserDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceTest {
    private final UserService userService;

    private CreateUserDto createUserDto(Long i) {
        return CreateUserDto.builder()
                .name("Username" + i)
                .email("mail" + i + "@mail.com")
                .build();
    }

    private UpdateUserDto updateUserDto(Long i) {
        return UpdateUserDto.builder()
                .name("Username" + i)
                .email("mail" + i + "@mail.com")
                .build();
    }

    @Test
    void createUserTest() {
        CreateUserDto createUserDto = createUserDto(1L);
        ReturnUserDto returnUserDto = userService.createUser(createUserDto);

        assertEquals(createUserDto.getName(), returnUserDto.getName());
        assertEquals(createUserDto.getEmail(), returnUserDto.getEmail());
        assertNotNull(returnUserDto.getId());
    }

    @Test
    void updateUserTest() {
        CreateUserDto createUserDto = createUserDto(1L);
        ReturnUserDto returnCreatedUserDto = userService.createUser(createUserDto);
        UpdateUserDto updateUserDto = updateUserDto(2L);
        ReturnUserDto returnUpdatedUserDto = userService.updateUser(returnCreatedUserDto.getId(), updateUserDto);

        assertEquals(updateUserDto.getName(), returnUpdatedUserDto.getName());
        assertEquals(updateUserDto.getEmail(), returnUpdatedUserDto.getEmail());
        assertEquals(returnUpdatedUserDto.getId(), returnCreatedUserDto.getId());
    }

    @Test
    void getUserByIdTest() {
        CreateUserDto createUserDto = createUserDto(1L);
        ReturnUserDto returnCreatedUserDto = userService.createUser(createUserDto);
        ReturnUserDto returnFindedUserDto = userService.getUserById(returnCreatedUserDto.getId());

        assertEquals(returnFindedUserDto.getName(), returnCreatedUserDto.getName());
        assertEquals(returnFindedUserDto.getEmail(), returnCreatedUserDto.getEmail());
        assertEquals(returnFindedUserDto.getId(), returnCreatedUserDto.getId());
    }

    @Test
    void getUsersTest() {
        CreateUserDto createUserDto1 = createUserDto(1L);
        ReturnUserDto returnCreatedUserDto1 = userService.createUser(createUserDto1);
        CreateUserDto createUserDto2 = createUserDto(2L);
        ReturnUserDto returnCreatedUserDto2 = userService.createUser(createUserDto2);
        List<ReturnUserDto> allUsers = userService.getUsers();

        assertEquals(allUsers.size(), 2);
        assertEquals(allUsers.getFirst().getEmail(), returnCreatedUserDto1.getEmail());
        assertEquals(allUsers.getLast().getId(), returnCreatedUserDto2.getId());
    }

    @Test
    void deleteUserTest() {
        CreateUserDto createUserDto1 = createUserDto(1L);
        userService.createUser(createUserDto1);
        CreateUserDto createUserDto2 = createUserDto(2L);
        ReturnUserDto returnCreatedUserDto2 = userService.createUser(createUserDto2);
        CreateUserDto createUserDto3 = createUserDto(3L);
        ReturnUserDto returnCreatedUserDto3 = userService.createUser(createUserDto3);
        userService.deleteUser(returnCreatedUserDto3.getId());
        List<ReturnUserDto> allUsers = userService.getUsers();

        assertEquals(allUsers.size(), 2);
        assertEquals(allUsers.getLast().getName(), returnCreatedUserDto2.getName());
        assertEquals(allUsers.getLast().getEmail(), returnCreatedUserDto2.getEmail());
        assertEquals(allUsers.getLast().getId(), returnCreatedUserDto2.getId());
    }

    @Test
    void emailValidationTest() {
        CreateUserDto createUserDto1 = CreateUserDto.builder()
                .name("Username1")
                .email(null)
                .build();
        CreateUserDto createUserDto2 = CreateUserDto.builder()
                .name("Username1")
                .email("")
                .build();

        CreateUserDto createUserDto3 = CreateUserDto.builder()
                .name("Username1")
                .email("mail@mail.com")
                .build();
        userService.createUser(createUserDto3);
        CreateUserDto createUserDto4 = CreateUserDto.builder()
                .name("Username2")
                .email("mail@mail.com")
                .build();

        assertThrows(ValidationException.class, () -> userService.createUser(createUserDto1));
        assertThrows(ValidationException.class, () -> userService.createUser(createUserDto2));
        assertThrows(EmailAlreadyInUseException.class, () -> userService.createUser(createUserDto4));
    }

    @Test
    void shouldThrowNotFoundException() {
        CreateUserDto createUserDto1 = createUserDto(1L);
        ReturnUserDto returnCreatedUserDto1 = userService.createUser(createUserDto1);
        userService.deleteUser(returnCreatedUserDto1.getId());
        UpdateUserDto updateUserDto = updateUserDto(2L);

        assertThrows(NotFoundException.class, () -> userService.deleteUser(returnCreatedUserDto1.getId()));
        assertThrows(NotFoundException.class, () -> userService.getUserById(returnCreatedUserDto1.getId()));
        assertThrows(NotFoundException.class, () -> userService.updateUser(
                returnCreatedUserDto1.getId(), updateUserDto));
    }
}
