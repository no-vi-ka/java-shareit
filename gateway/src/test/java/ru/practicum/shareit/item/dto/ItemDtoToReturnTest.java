package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.user.dto.ReturnUserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class ItemDtoToReturnTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeItemDtoToReturn() throws Exception {
        ReturnUserDto userDto = new ReturnUserDto(1L, "Username", "mail@mail.com");
        ItemDtoToReturn itemDtoForRequest = new ItemDtoToReturn();
        itemDtoForRequest.setId(1L);
        itemDtoForRequest.setName("Itemname");
        itemDtoForRequest.setDescription("Description.");
        itemDtoForRequest.setAvailable(true);
        itemDtoForRequest.setOwner(userDto);
        itemDtoForRequest.setRequestId(1L);

        String json = objectMapper.writeValueAsString(itemDtoForRequest);
        ItemDtoToReturn deserializedDto = objectMapper.readValue(json, ItemDtoToReturn.class);

        assertEquals(itemDtoForRequest.getId(), deserializedDto.getId());
        assertEquals(itemDtoForRequest.getName(), deserializedDto.getName());
        assertEquals(itemDtoForRequest.getDescription(), deserializedDto.getDescription());
        assertEquals(itemDtoForRequest.getAvailable(), deserializedDto.getAvailable());
        assertEquals(itemDtoForRequest.getOwner().getId(), deserializedDto.getOwner().getId());
        assertEquals(itemDtoForRequest.getOwner().getName(), deserializedDto.getOwner().getName());
        assertEquals(itemDtoForRequest.getOwner().getEmail(), deserializedDto.getOwner().getEmail());
        assertEquals(itemDtoForRequest.getRequestId(), deserializedDto.getRequestId());
    }
}
