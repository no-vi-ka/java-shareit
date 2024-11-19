package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class ReturnUserDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeReturnUserDto() throws Exception {
        ReturnUserDto returnUserDto = new ReturnUserDto();
        returnUserDto.setId(1L);
        returnUserDto.setName("Username");
        returnUserDto.setEmail("mail@mail.com");

        String json = objectMapper.writeValueAsString(returnUserDto);
        ReturnUserDto deserializedDto = objectMapper.readValue(json, ReturnUserDto.class);

        assertEquals(returnUserDto.getId(), deserializedDto.getId());
        assertEquals(returnUserDto.getName(), deserializedDto.getName());
        assertEquals(returnUserDto.getEmail(), deserializedDto.getEmail());
    }
}
