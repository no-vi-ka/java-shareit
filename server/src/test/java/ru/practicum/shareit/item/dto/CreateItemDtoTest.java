package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class CreateItemDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeCreateItemDto() throws Exception {
        CreateItemDto createItemDto = new CreateItemDto();
        createItemDto.setName("Username");
        createItemDto.setDescription("Description.");
        createItemDto.setAvailable(true);
        createItemDto.setRequestId(1L);

        String json = objectMapper.writeValueAsString(createItemDto);
        CreateItemDto deserializedDto = objectMapper.readValue(json, CreateItemDto.class);

        assertEquals(createItemDto.getName(), deserializedDto.getName());
        assertEquals(createItemDto.getDescription(), deserializedDto.getDescription());
        assertEquals(createItemDto.getAvailable(), deserializedDto.getAvailable());
        assertEquals(createItemDto.getRequestId(), deserializedDto.getRequestId());
    }
}
