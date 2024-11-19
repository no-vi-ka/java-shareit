package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.user.dto.ReturnUserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class ItemDtoForRequestTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeCreateItemDto() throws Exception {
        ReturnUserDto userDto = new ReturnUserDto(1L, "Username", "mail@mail.com");
        ItemDtoForRequest itemDtoForRequest = new ItemDtoForRequest();
        itemDtoForRequest.setId(1L);
        itemDtoForRequest.setName("Itemname");
        itemDtoForRequest.setOwner(userDto);

        String json = objectMapper.writeValueAsString(itemDtoForRequest);
        ItemDtoForRequest deserializedDto = objectMapper.readValue(json, ItemDtoForRequest.class);

        assertEquals(itemDtoForRequest.getName(), deserializedDto.getName());
        assertEquals(itemDtoForRequest.getOwner().getId(), deserializedDto.getOwner().getId());
        assertEquals(itemDtoForRequest.getOwner().getName(), deserializedDto.getOwner().getName());
        assertEquals(itemDtoForRequest.getOwner().getEmail(), deserializedDto.getOwner().getEmail());
    }
}
