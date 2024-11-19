package ru.practicum.shareit.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDtoToReturn;
import ru.practicum.shareit.item.dto.ItemWithCommentsDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.ReturnUserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ItemService itemService;

    @Autowired
    MockMvc mockMvc;

    private ItemDtoToReturn returnItemDto(Long i) {
        return ItemDtoToReturn.builder()
                .id(i)
                .name("Username" + i)
                .description("Description" + i)
                .available(true)
                .owner(new ReturnUserDto())
                .requestId(i).
                build();
    }

    private ItemWithCommentsDto returnItemWithCommentsDto(Long i) {
        return ItemWithCommentsDto.builder()
                .id(i)
                .name("Username" + i)
                .description("Description" + i)
                .available(true)
                .requestId(i)
                .lastBooking(new BookingDto())
                .nextBooking(new BookingDto())
                .comments(new ArrayList<>())
                .build();
    }

    private CommentDto getCommentDto(Long i) {
        return CommentDto.builder()
                .id(i)
                .text("Text" + i)
                .created(LocalDateTime.now())
                .authorName("Username" + i)
                .build();
    }

    @Test
    void createItemTest() throws Exception {
        ItemDtoToReturn itemDtoToReturn = returnItemDto(1L);

        when(itemService.createItem(any(), any()))
                .thenReturn(itemDtoToReturn);

        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(new ItemDtoToReturn()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(itemDtoToReturn.getId()), Long.class))
                .andExpect(jsonPath("$.name", Matchers.is(itemDtoToReturn.getName())))
                .andExpect(jsonPath("$.description", Matchers.is(itemDtoToReturn.getDescription())))
                .andExpect(jsonPath("$.available", Matchers.is(itemDtoToReturn.getAvailable())))
                .andExpect(jsonPath("$.owner", Matchers.notNullValue()))
                .andExpect(jsonPath("$.requestId", Matchers.is(itemDtoToReturn.getRequestId()), Long.class));
    }

    @Test
    void updateItemTest() throws Exception {
        ItemDtoToReturn itemDto = returnItemDto(1L);

        when(itemService.updateItem(any(), any(), any()))
                .thenReturn(itemDto);

        mockMvc.perform(patch("/items/" + itemDto.getId())
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(new ItemDtoToReturn()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", Matchers.is(itemDto.getName())))
                .andExpect(jsonPath("$.description", Matchers.is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", Matchers.is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.owner", Matchers.notNullValue()))
                .andExpect(jsonPath("$.requestId", Matchers.is(itemDto.getRequestId()), Long.class));
    }

    @Test
    void getItemById() throws Exception {
        ItemWithCommentsDto itemWithBookingDto = returnItemWithCommentsDto(1L);

        when(itemService.getItemById(any()))
                .thenReturn(itemWithBookingDto);

        mockMvc.perform(get("/items/" + itemWithBookingDto.getId())
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(itemWithBookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", Matchers.is(itemWithBookingDto.getName())))
                .andExpect(jsonPath("$.description", Matchers.is(itemWithBookingDto.getDescription())))
                .andExpect(jsonPath("$.available", Matchers.is(itemWithBookingDto.getAvailable())))
                .andExpect(jsonPath("$.requestId", Matchers.notNullValue()))
                .andExpect(jsonPath("$.lastBooking", Matchers.notNullValue()))
                .andExpect(jsonPath("$.nextBooking", Matchers.notNullValue()))
                .andExpect(jsonPath("$.comments", Matchers.notNullValue()));
    }

    @Test
    void getItemsByUserIdTest() throws Exception {
        List<ItemDtoToReturn> itemDtoToReturnList = List.of(returnItemDto(1L),
                returnItemDto(2L));

        when(itemService.getItemsByUserId(any()))
                .thenReturn(itemDtoToReturnList);

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(itemDtoToReturnList.get(0).getId()), Long.class))
                .andExpect(jsonPath("$[0].name", Matchers.is(itemDtoToReturnList.get(0).getName())))
                .andExpect(jsonPath("$[0].description", Matchers.is(itemDtoToReturnList.get(0).getDescription())))
                .andExpect(jsonPath("$[1].id", Matchers.is(itemDtoToReturnList.get(1).getId()), Long.class))
                .andExpect(jsonPath("$[1].name", Matchers.is(itemDtoToReturnList.get(1).getName())))
                .andExpect(jsonPath("$[1].description", Matchers.is(itemDtoToReturnList.get(1).getDescription())));
    }

    @Test
    void getItemListBySearch_shouldReturnItemDtoList() throws Exception {
        List<ItemDtoToReturn> itemDtoToReturnList = List.of(returnItemDto(1L),
                returnItemDto(2L));

        when(itemService.searchItems(any(), any()))
                .thenReturn(itemDtoToReturnList);

        mockMvc.perform(get("/items/search?text=text")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(itemDtoToReturnList.get(0).getId()), Long.class))
                .andExpect(jsonPath("$[0].name", Matchers.is(itemDtoToReturnList.get(0).getName())))
                .andExpect(jsonPath("$[0].description", Matchers.is(itemDtoToReturnList.get(0).getDescription())))
                .andExpect(jsonPath("$[1].id", Matchers.is(itemDtoToReturnList.get(1).getId()), Long.class))
                .andExpect(jsonPath("$[1].name", Matchers.is(itemDtoToReturnList.get(1).getName())))
                .andExpect(jsonPath("$[1].description", Matchers.is(itemDtoToReturnList.get(1).getDescription())));
    }

    @Test
    void addComment_shouldReturnCommentDto() throws Exception {
        CommentDto commentDto = getCommentDto(1L);

        when(itemService.createComment(any(), any(), any()))
                .thenReturn(commentDto);

        mockMvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(new CommentDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(commentDto.getId()), Long.class))
                .andExpect(jsonPath("$.text", Matchers.is(commentDto.getText())))
                .andExpect(jsonPath("$.authorName", Matchers.is(commentDto.getAuthorName())))
                .andExpect(jsonPath("$.created", Matchers.containsString(commentDto.getCreated().toString().substring(0, 25))));
    }
}
