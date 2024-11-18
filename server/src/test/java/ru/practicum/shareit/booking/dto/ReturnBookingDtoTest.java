package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.item.dto.ItemDtoToReturn;
import ru.practicum.shareit.status.Status;
import ru.practicum.shareit.user.dto.ReturnUserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class ReturnBookingDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeBookingDto() throws Exception {
        ReturnUserDto userDto = new ReturnUserDto(1L, "Username1", "mail1@mail.com");
        ItemDtoToReturn itemToSet = new ItemDtoToReturn();
        itemToSet.setId(1L);
        itemToSet.setName("Itemname");
        itemToSet.setDescription("Description.");
        itemToSet.setAvailable(true);
        itemToSet.setOwner(userDto);
        itemToSet.setRequestId(1L);

        ReturnUserDto bookerToSet = new ReturnUserDto();
        bookerToSet.setId(2L);
        bookerToSet.setName("Username2");
        bookerToSet.setEmail("mail2@mail.com");

        ReturnBookingDto bookingDto = new ReturnBookingDto();
        bookingDto.setId(1L);
        bookingDto.setStart(LocalDateTime.of(2025, 11, 18, 11, 11, 11));
        bookingDto.setEnd(LocalDateTime.of(2025, 11, 18, 11, 11, 12));
        bookingDto.setItem(itemToSet);
        bookingDto.setBooker(bookerToSet);
        bookingDto.setStatus(Status.WAITING);

        String json = objectMapper.writeValueAsString(bookingDto);
        ReturnBookingDto deserializedDto = objectMapper.readValue(json, ReturnBookingDto.class);

        assertEquals(bookingDto.getId(), deserializedDto.getId());
        assertEquals(bookingDto.getEnd(), deserializedDto.getEnd());
        assertEquals(bookingDto.getStart(), deserializedDto.getStart());

        assertEquals(bookingDto.getItem().getId(), deserializedDto.getItem().getId());
        assertEquals(bookingDto.getItem().getName(), deserializedDto.getItem().getName());
        assertEquals(bookingDto.getItem().getDescription(), deserializedDto.getItem().getDescription());

        assertEquals(bookingDto.getBooker().getId(), deserializedDto.getBooker().getId());
        assertEquals(bookingDto.getBooker().getName(), deserializedDto.getBooker().getName());
        assertEquals(bookingDto.getBooker().getEmail(), deserializedDto.getBooker().getEmail());

        assertEquals(bookingDto.getStatus(), deserializedDto.getStatus());
    }
}
