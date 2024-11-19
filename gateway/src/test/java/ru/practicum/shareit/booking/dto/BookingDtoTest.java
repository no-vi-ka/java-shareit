package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class BookingDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeBookingDto() throws Exception {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setStart(LocalDateTime.of(2025, 11, 18, 11, 11, 11));
        bookingDto.setEnd(LocalDateTime.of(2025, 11, 18, 11, 11, 12));

        String json = objectMapper.writeValueAsString(bookingDto);
        BookingDto deserializedDto = objectMapper.readValue(json, BookingDto.class);

        assertEquals(bookingDto.getItemId(), deserializedDto.getItemId());
        assertEquals(bookingDto.getEnd(), deserializedDto.getEnd());
        assertEquals(bookingDto.getStart(), deserializedDto.getStart());
    }
}
