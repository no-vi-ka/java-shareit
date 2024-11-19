package ru.practicum.shareit.item.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.NotOwnerException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
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
public class ItemServiceTest {
    private final ItemRequestService itemRequestService;
    private final UserService userService;
    private final ItemService itemService;
    private final BookingService bookingService;

    private CreateItemDto createItemDto(Long i) {
        return CreateItemDto.builder()
                .name("Itemname" + i)
                .description("Description" + i)
                .available(true)
                .build();
    }

    private UpdateItemDto updateItemDto(Long i) {
        return UpdateItemDto.builder()
                .name("Itemname" + i)
                .description("Description" + i)
                .available(false)
                .build();
    }

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
    void createItemTest() {
        CreateUserDto createUserDto1 = createUserDto(1L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateItemDto createItemDto = createItemDto(1L);
        ItemDtoToReturn itemDtoToReturn = itemService.createItem(createItemDto, returnUserDto1.getId());

        assertEquals(itemDtoToReturn.getName(), createItemDto.getName());
        assertEquals(itemDtoToReturn.getDescription(), createItemDto.getDescription());
        assertEquals(itemDtoToReturn.getAvailable(), createItemDto.getAvailable());
        assertEquals(itemDtoToReturn.getOwner().getId(), returnUserDto1.getId());
        assertNull(itemDtoToReturn.getRequestId());
    }

    @Test
    void updateItemTest() {
        CreateUserDto createUserDto1 = createUserDto(2L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateItemDto createItemDto = createItemDto(2L);
        ItemDtoToReturn itemDtoToReturn = itemService.createItem(createItemDto, returnUserDto1.getId());
        UpdateItemDto updateItemDto = updateItemDto(3L);
        ItemDtoToReturn updated = itemService.updateItem(
                updateItemDto, returnUserDto1.getId(), itemDtoToReturn.getId());

        assertEquals(updated.getName(), updateItemDto.getName());
        assertEquals(updated.getDescription(), updated.getDescription());
        assertEquals(updated.getAvailable(), updated.getAvailable());
    }

    @Test
    void getItemByIdTest() {
        CreateUserDto createUserDto1 = createUserDto(3L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateItemDto createItemDto = createItemDto(3L);
        ItemDtoToReturn itemDtoToReturn = itemService.createItem(createItemDto, returnUserDto1.getId());

        ItemWithCommentsDto finded = itemService.getItemById(itemDtoToReturn.getId());

        assertEquals(finded.getId(), itemDtoToReturn.getId());
        assertEquals(finded.getName(), itemDtoToReturn.getName());
        assertEquals(finded.getDescription(), itemDtoToReturn.getDescription());
        assertEquals(finded.getAvailable(), itemDtoToReturn.getAvailable());
        assertEquals(finded.getRequestId(), itemDtoToReturn.getRequestId());
    }

    @Test
    void getItemsByUserIdTest() {
        CreateUserDto createUserDto1 = createUserDto(4L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateUserDto createUserDto2 = createUserDto(5L);
        ReturnUserDto returnUserDto2 = userService.createUser(createUserDto2);
        CreateItemDto createItemDto1 = createItemDto(4L);
        ItemDtoToReturn itemDtoToReturn1 = itemService.createItem(createItemDto1, returnUserDto1.getId());
        CreateItemDto createItemDto2 = createItemDto(5L);
        ItemDtoToReturn itemDtoToReturn2 = itemService.createItem(createItemDto2, returnUserDto1.getId());

        List<ItemDtoToReturn> finded = itemService.getItemsByUserId(returnUserDto1.getId());

        assertEquals(finded.size(), 2);
        assertEquals(finded.getFirst().getId(), itemDtoToReturn1.getId());
        assertEquals(finded.getLast().getId(), itemDtoToReturn2.getId());
    }

    @Test
    void searchItemsTest() {
        String text = "text";
        String emptyText = "";
        CreateUserDto createUserDto1 = createUserDto(6L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateItemDto createItemDto1 = createItemDto(6L);
        ItemDtoToReturn itemDtoToReturn1 = itemService.createItem(createItemDto1, returnUserDto1.getId());
        CreateItemDto createItemDto2 = createItemDto(7L);
        ItemDtoToReturn itemDtoToReturn2 = itemService.createItem(createItemDto2, returnUserDto1.getId());
        UpdateItemDto updateItemDto = UpdateItemDto.builder()
                .description("text.").build();
        ItemDtoToReturn updated = itemService.updateItem(
                updateItemDto, returnUserDto1.getId(), itemDtoToReturn1.getId());

        List<ItemDtoToReturn> finded = itemService.searchItems(returnUserDto1.getId(), text);
        List<ItemDtoToReturn> findedWithEmptyText = itemService.searchItems(returnUserDto1.getId(), emptyText);

        assertEquals(finded.size(), 1);
        assertEquals(finded.getFirst().getName(), updated.getName());
        assertEquals(finded.getFirst().getDescription(), updated.getDescription());
        assertEquals(finded.getFirst().getAvailable(), updated.getAvailable());
        assertEquals(finded.getFirst().getOwner().getId(), updated.getOwner().getId());
        assertEquals(finded.getFirst().getId(), updated.getId());
        assertEquals(findedWithEmptyText, Collections.emptyList());
        assertEquals(findedWithEmptyText.size(), 0);
    }

    @Test
    void shouldThrownExceptions() {
        String text = "text";
        CommentDto commentDto = CommentDto.builder()
                .text("Text.")
                .build();

        CreateUserDto createUserDto1 = createUserDto(7L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);

        CreateUserDto createUserDto2 = createUserDto(8L);
        ReturnUserDto returnUserDto2 = userService.createUser(createUserDto2);

        CreateUserDto createUserDto3 = createUserDto(9L);
        ReturnUserDto returnUserDto3 = userService.createUser(createUserDto3);

        CreateItemDto createItemDto1 = createItemDto(8L);
        CreateItemDto createItemDto2 = createItemDto(9L);
        ItemDtoToReturn itemDtoToReturn2 = itemService.createItem(createItemDto2, returnUserDto1.getId());
        CreateItemDto createItemDto3 = createItemDto(10L);
        ItemDtoToReturn itemDtoToReturn3 = itemService.createItem(createItemDto3, returnUserDto2.getId());

        assertThrows(NotOwnerException.class, () -> itemService.deleteItem(itemDtoToReturn2.getId(), returnUserDto2.getId()));

        UpdateItemDto updateItemDto = updateItemDto(3L);

        userService.deleteUser(returnUserDto1.getId());

        assertThrows(NotFoundException.class, () -> itemService.createItem(createItemDto1, returnUserDto1.getId()));
        assertThrows(NotFoundException.class, () -> itemService.updateItem(
                updateItemDto, returnUserDto1.getId(), itemDtoToReturn2.getId()));
        assertThrows(NotOwnerException.class, () -> itemService.updateItem(
                updateItemDto, returnUserDto2.getId(), itemDtoToReturn2.getId()));
        assertThrows(NotFoundException.class, () -> itemService.searchItems(returnUserDto1.getId(), text));
        assertThrows(NotFoundException.class, () -> itemService.createComment(
                returnUserDto1.getId(), itemDtoToReturn2.getId(), commentDto));
        assertThrows(NotOwnerException.class, () -> itemService.createComment(
                returnUserDto3.getId(), itemDtoToReturn3.getId(), commentDto));

        itemService.deleteItem(itemDtoToReturn3.getId(), returnUserDto2.getId());

        assertThrows(NotFoundException.class, () -> itemService.getItemById(itemDtoToReturn3.getId()));
        assertThrows(NotFoundException.class, () -> itemService.deleteItem(itemDtoToReturn3.getId(), returnUserDto2.getId()));
        assertThrows(NotFoundException.class, () -> itemService.createComment(
                returnUserDto2.getId(), itemDtoToReturn3.getId(), commentDto));
    }
}
