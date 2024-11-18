//package ru.practicum.shareit.user.controller;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.practicum.shareit.user.dto.ReturnUserDto;
//import ru.practicum.shareit.user.service.UserService;
//
//@WebMvcTest(controllers = UserController.class)
//public class UserControllerTest {
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean
//    UserService userService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    private ReturnUserDto getUserDto(Long i) {
//        return  ReturnUserDto.builder()
//                .id(i)
//                .name("Username")
//                .email("mail" + i + "mail.com")
//                .build();
//    }
//}
