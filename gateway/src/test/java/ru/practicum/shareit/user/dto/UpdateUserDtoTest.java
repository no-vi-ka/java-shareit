package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@JsonTest
public class UpdateUserDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeUpdateUserDto() throws Exception {
        UpdateUserDto userUpdateDto = new UpdateUserDto();
        userUpdateDto.setName("Updated Username");
        userUpdateDto.setEmail("updated@mail.com");

        String json = objectMapper.writeValueAsString(userUpdateDto);
        UpdateUserDto deserializedDto = objectMapper.readValue(json, UpdateUserDto.class);

        assertEquals(userUpdateDto.getName(), deserializedDto.getName());
        assertEquals(userUpdateDto.getEmail(), deserializedDto.getEmail());
    }
}
