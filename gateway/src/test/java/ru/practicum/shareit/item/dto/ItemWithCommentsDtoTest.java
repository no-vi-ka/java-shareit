package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class ItemWithCommentsDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeItemWithCommentsDto() throws Exception {
        ItemWithCommentsDto itemWithCommentsDto = new ItemWithCommentsDto();
        itemWithCommentsDto.setId(1L);
        itemWithCommentsDto.setName("Itemname");
        itemWithCommentsDto.setDescription("Description.");
        itemWithCommentsDto.setAvailable(true);
        itemWithCommentsDto.setRequestId(1L);
        itemWithCommentsDto.setComments(Collections.emptyList());
        itemWithCommentsDto.setLastBooking(null);
        itemWithCommentsDto.setNextBooking(null);

        String json = objectMapper.writeValueAsString(itemWithCommentsDto);
        ItemWithCommentsDto deserializedDto = objectMapper.readValue(json, ItemWithCommentsDto.class);

        assertEquals(itemWithCommentsDto.getId(), deserializedDto.getId());
        assertEquals(itemWithCommentsDto.getName(), deserializedDto.getName());
        assertEquals(itemWithCommentsDto.getDescription(), deserializedDto.getDescription());
        assertEquals(itemWithCommentsDto.getComments(), deserializedDto.getComments());
        assertEquals(itemWithCommentsDto.getRequestId(), deserializedDto.getRequestId());
        assertEquals(itemWithCommentsDto.getLastBooking(), deserializedDto.getLastBooking());
        assertEquals(itemWithCommentsDto.getNextBooking(), deserializedDto.getNextBooking());
        assertEquals(itemWithCommentsDto.getAvailable(), deserializedDto.getAvailable());
    }
}
