package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class CommentDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeCommentDto() throws Exception {
        CommentDto userDto = new CommentDto();
        userDto.setId(1L);
        userDto.setText("Text.");
        userDto.setAuthorName("Username");
        userDto.setCreated(LocalDateTime.now());

        String json = objectMapper.writeValueAsString(userDto);
        CommentDto deserializedDto = objectMapper.readValue(json, CommentDto.class);

        assertEquals(userDto.getId(), deserializedDto.getId());
        assertEquals(userDto.getText(), deserializedDto.getText());
        assertEquals(userDto.getAuthorName(), deserializedDto.getAuthorName());
        assertEquals(userDto.getCreated(), deserializedDto.getCreated());
    }
}
