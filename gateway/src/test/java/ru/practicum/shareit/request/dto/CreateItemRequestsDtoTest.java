package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class CreateItemRequestsDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeCreateItemRequestsDto() throws Exception {
        CreateItemRequestDto itemRequestDto = new CreateItemRequestDto();
        itemRequestDto.setDescription("Test description");
        itemRequestDto.setUserId(1L);

        String json = objectMapper.writeValueAsString(itemRequestDto);
        CreateItemRequestDto deserializedDto = objectMapper.readValue(json, CreateItemRequestDto.class);

        assertEquals(itemRequestDto.getDescription(), deserializedDto.getDescription());
        assertEquals(itemRequestDto.getUserId(), deserializedDto.getUserId());
    }
}
