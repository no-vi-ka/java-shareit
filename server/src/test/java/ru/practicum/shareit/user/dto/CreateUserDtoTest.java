package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class CreateUserDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeCreateUserDto() throws Exception {
        CreateUserDto userDto = new CreateUserDto();
        userDto.setName("Username");
        userDto.setEmail("mail@mail.com");

        String json = objectMapper.writeValueAsString(userDto);
        CreateUserDto deserializedDto = objectMapper.readValue(json, CreateUserDto.class);

        assertEquals(userDto.getName(), deserializedDto.getName());
        assertEquals(userDto.getEmail(), deserializedDto.getEmail());
    }
}
