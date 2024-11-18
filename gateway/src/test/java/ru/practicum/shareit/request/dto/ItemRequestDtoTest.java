package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.user.dto.ReturnUserDto;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class ItemRequestDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeItemRequestDto() throws Exception {
        ReturnUserDto userDto = new ReturnUserDto(1L, "Username", "mail@mail.com");
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setDescription("Description");
        itemRequestDto.setRequestor(userDto);
        itemRequestDto.setCreated(LocalDateTime.now());
        itemRequestDto.setItems(Collections.emptyList());

        String json = objectMapper.writeValueAsString(itemRequestDto);
        ItemRequestDto deserializedDto = objectMapper.readValue(json, ItemRequestDto.class);

        assertEquals(itemRequestDto.getDescription(), deserializedDto.getDescription());
        assertEquals(itemRequestDto.getRequestor().getId(), deserializedDto.getRequestor().getId());
        assertEquals(itemRequestDto.getRequestor().getName(), deserializedDto.getRequestor().getName());
        assertEquals(itemRequestDto.getRequestor().getEmail(), deserializedDto.getRequestor().getEmail());
        assertEquals(itemRequestDto.getCreated(), deserializedDto.getCreated());
        assertEquals(itemRequestDto.getItems(), deserializedDto.getItems());
    }
}
