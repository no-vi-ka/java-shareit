package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class UpdateItemDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeUpdateItemDto() throws Exception {
        UpdateItemDto updateItemDto = new UpdateItemDto();
        updateItemDto.setName("Username");
        updateItemDto.setDescription("Description.");
        updateItemDto.setAvailable(true);

        String json = objectMapper.writeValueAsString(updateItemDto);
        UpdateItemDto deserializedDto = objectMapper.readValue(json, UpdateItemDto.class);

        assertEquals(updateItemDto.getName(), deserializedDto.getName());
        assertEquals(updateItemDto.getDescription(), deserializedDto.getDescription());
        assertEquals(updateItemDto.getAvailable(), deserializedDto.getAvailable());
    }
}
