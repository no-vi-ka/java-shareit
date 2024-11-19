package ru.practicum.shareit.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.dto.ReturnUserDto;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    private ReturnUserDto returnUserDto(Long i) {
        return ReturnUserDto.builder()
                .id(i)
                .name("Username")
                .email("mail" + i + "mail.com")
                .build();
    }

    @Test
    void createUserTest() throws Exception {
        ReturnUserDto returnUserDto = returnUserDto(1L);

        when(userService.createUser(any()))
                .thenReturn(returnUserDto);

        mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(returnUserDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(returnUserDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", Matchers.is(returnUserDto.getName())))
                .andExpect(jsonPath("$.email", Matchers.is(returnUserDto.getEmail())));

        verify(userService, times(1))
                .createUser(any());
    }

    @Test
    void updateUserTest() throws Exception {
        ReturnUserDto returnUserDto = returnUserDto(1L);

        when(userService.updateUser(any(), any()))
                .thenReturn(returnUserDto);

        mockMvc.perform(patch("/users/1")
                        .content(objectMapper.writeValueAsString(returnUserDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(returnUserDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", Matchers.is(returnUserDto.getName())))
                .andExpect(jsonPath("$.email", Matchers.is(returnUserDto.getEmail())));

        verify(userService, times(1))
                .updateUser(any(), any());
    }

    @Test
    void getAllUsersTest() throws Exception {
        ReturnUserDto userDto = returnUserDto(1L);

        when(userService.getUsers())
                .thenReturn(List.of(userDto));

        mockMvc.perform(get("/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$[0].name", Matchers.is(userDto.getName())))
                .andExpect(jsonPath("$[0].email", Matchers.is(userDto.getEmail())));

        verify(userService, times(1))
                .getUsers();
    }

    @Test
    void getUserByIdTest() throws Exception {
        ReturnUserDto returnUserDto = returnUserDto(1L);

        when(userService.getUserById(any()))
                .thenReturn(returnUserDto);

        mockMvc.perform(get("/users/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(returnUserDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", Matchers.is(returnUserDto.getName())))
                .andExpect(jsonPath("$.email", Matchers.is(returnUserDto.getEmail())));

        verify(userService, times(1))
                .getUserById(any());
    }

    @Test
    void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/users/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk());

        verify(userService, times(1))
                .deleteUser(any());
    }
}
