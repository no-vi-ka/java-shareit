package ru.practicum.shareit.request.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ReturnUserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestServiceTest {
    private final ItemRequestService itemRequestService;
    private final UserService userService;

    private CreateItemRequestDto createItemRequestDto(Long i) {
        return CreateItemRequestDto.builder()
                .description("Description" + i)
                .build();
    }

    private CreateUserDto createUserDto(Long i) {
        return CreateUserDto.builder()
                .name("Username" + i)
                .email("mail" + i + "@mail.com")
                .build();
    }

    @Test
    void createItemRequestTest() {
        CreateUserDto createUserDto1 = createUserDto(1L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateItemRequestDto createItemRequestDto = createItemRequestDto(1L);
        ItemRequestDto itemRequestDto = itemRequestService.createItemRequest(
                returnUserDto1.getId(), createItemRequestDto);

        assertEquals(itemRequestDto.getRequestor().getId(), returnUserDto1.getId());
        assertEquals(itemRequestDto.getDescription(), createItemRequestDto.getDescription());
    }

    @Test
    void getItemRequestByIdTest() {
        CreateUserDto createUserDto1 = createUserDto(2L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateItemRequestDto createItemRequestDto = createItemRequestDto(2L);
        ItemRequestDto itemRequestDto = itemRequestService.createItemRequest(
                returnUserDto1.getId(), createItemRequestDto);

        ItemRequestDto itemRequestDtoFinded = itemRequestService.getItemRequestById(
                returnUserDto1.getId(), itemRequestDto.getId());

        assertEquals(itemRequestDtoFinded.getId(), itemRequestDto.getId());
        assertEquals(itemRequestDtoFinded.getCreated(), itemRequestDto.getCreated());
        assertEquals(itemRequestDtoFinded.getDescription(), itemRequestDto.getDescription());
        assertEquals(itemRequestDtoFinded.getRequestor().getId(), itemRequestDto.getRequestor().getId());
    }

    @Test
    void getItemRequestsByRequestorIdTest() {
        CreateUserDto createUserDto1 = createUserDto(2L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateItemRequestDto createItemRequestDto1 = createItemRequestDto(3L);
        ItemRequestDto itemRequestDto1 = itemRequestService.createItemRequest(
                returnUserDto1.getId(), createItemRequestDto1);
        CreateItemRequestDto createItemRequestDto2 = createItemRequestDto(4L);
        ItemRequestDto itemRequestDto2 = itemRequestService.createItemRequest(
                returnUserDto1.getId(), createItemRequestDto2);

        List<ItemRequestDto> finded = itemRequestService.getAllItemRequestForRequestor(returnUserDto1.getId());

        assertEquals(finded.size(), 2);
        assertEquals(finded.getLast().getId(), itemRequestDto1.getId());
        assertEquals(finded.getFirst().getDescription(), itemRequestDto2.getDescription());
    }

    @Test
    void getAllItemRequestTest() {
        CreateUserDto createUserDto1 = createUserDto(3L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateUserDto createUserDto2 = createUserDto(4L);
        ReturnUserDto returnUserDto2 = userService.createUser(createUserDto2);
        CreateItemRequestDto createItemRequestDto1 = createItemRequestDto(5L);
        ItemRequestDto itemRequestDto1 = itemRequestService.createItemRequest(
                returnUserDto1.getId(), createItemRequestDto1);
        CreateItemRequestDto createItemRequestDto2 = createItemRequestDto(6L);
        ItemRequestDto itemRequestDto2 = itemRequestService.createItemRequest(
                returnUserDto2.getId(), createItemRequestDto2);

        List<ItemRequestDto> finded = itemRequestService.getAllItemRequest(returnUserDto1.getId());

        assertEquals(finded.size(), 1);
        assertEquals(finded.getFirst().getId(), itemRequestDto2.getId());
        assertEquals(finded.getFirst().getCreated(), itemRequestDto2.getCreated());
        assertEquals(finded.getFirst().getDescription(), itemRequestDto2.getDescription());
        assertNotEquals(itemRequestDto1.getId(), finded.getFirst().getId());
    }

    @Test
    void shouldThrownNotFoundException() {
        CreateUserDto createUserDto1 = createUserDto(5L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);

        assertThrows(NotFoundException.class, () -> itemRequestService.getItemRequestById(
                returnUserDto1.getId(), 100L));

        CreateItemRequestDto createItemRequestDto1 = createItemRequestDto(7L);
        ItemRequestDto itemRequestDto1 = itemRequestService.createItemRequest(
                returnUserDto1.getId(), createItemRequestDto1);
        userService.deleteUser(returnUserDto1.getId());
        CreateItemRequestDto createItemRequestDto2 = createItemRequestDto(8L);

        assertThrows(NotFoundException.class, () -> itemRequestService.createItemRequest(
                returnUserDto1.getId(), createItemRequestDto2));
        assertThrows(NotFoundException.class, () -> itemRequestService.getAllItemRequestForRequestor(
                returnUserDto1.getId()));
        assertThrows(NotFoundException.class, () -> itemRequestService.getAllItemRequest(
                returnUserDto1.getId()));
    }

    @Test
    void shouldReturnEmptyList() {
        CreateUserDto createUserDto1 = createUserDto(1L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateUserDto createUserDto2 = createUserDto(2L);
        ReturnUserDto returnUserDto2 = userService.createUser(createUserDto2);

        List<ItemRequestDto> finded = itemRequestService.getAllItemRequest(returnUserDto1.getId());
        List<ItemRequestDto> finded2 = itemRequestService.getAllItemRequestForRequestor(returnUserDto2.getId());

        assertEquals(finded, Collections.emptyList());
        assertEquals(finded2, Collections.emptyList());
    }
}
