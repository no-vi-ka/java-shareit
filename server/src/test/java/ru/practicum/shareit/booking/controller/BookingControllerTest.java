package ru.practicum.shareit.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDtoToReturn;
import ru.practicum.shareit.status.Status;
import ru.practicum.shareit.user.dto.ReturnUserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookingService bookingService;

    @Autowired
    MockMvc mockMvc;

    private ReturnBookingDto returnBookingDto(Long i) {
        return ReturnBookingDto.builder()
                .id(i)
                .start(LocalDateTime.now().plusMinutes(i))
                .end(LocalDateTime.now().plusHours(i))
                .item(new ItemDtoToReturn())
                .booker(new ReturnUserDto())
                .status(Status.WAITING).build();
    }

    @Test
    void createBookingTest() throws Exception {
        ReturnBookingDto returnBookingDto = returnBookingDto(1L);

        when(bookingService.createBooking(any(), any()))
                .thenReturn(returnBookingDto);

        mockMvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(new ReturnBookingDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(returnBookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.item", Matchers.notNullValue()))
                .andExpect(jsonPath("$.booker", Matchers.notNullValue()))
                .andExpect(jsonPath("$.status", Matchers.is(returnBookingDto.getStatus().name())))
                .andExpect(jsonPath("$.start", Matchers.containsString(
                        returnBookingDto.getStart().toString().substring(0, 24))))
                .andExpect(jsonPath("$.end", Matchers.containsString(
                        returnBookingDto.getEnd().toString().substring(0, 24))));

        verify(bookingService, times(1))
                .createBooking(any(), any());
    }

    @Test
    void setApproveTest() throws Exception {
        ReturnBookingDto returnBookingDto = returnBookingDto(1L);
        returnBookingDto.setStatus(Status.APPROVED);

        when(bookingService.setApprove(any(), any(), any()))
                .thenReturn(returnBookingDto);

        mockMvc.perform(patch("/bookings/" + returnBookingDto.getId() + "?approved=true")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(returnBookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.item", Matchers.notNullValue()))
                .andExpect(jsonPath("$.booker", Matchers.notNullValue()))
                .andExpect(jsonPath("$.status", Matchers.is(returnBookingDto.getStatus().name())))
                .andExpect(jsonPath("$.start", Matchers.containsString(
                        returnBookingDto.getStart().toString().substring(0, 24))))
                .andExpect(jsonPath("$.end", Matchers.containsString(
                        returnBookingDto.getEnd().toString().substring(0, 24))));

        verify(bookingService, times(1))
                .setApprove(any(), any(), any());
    }

    @Test
    void getBookingByIdTest() throws Exception {
        ReturnBookingDto returnBookingDto = returnBookingDto(1L);

        when(bookingService.getBookingById(any(), any()))
                .thenReturn(returnBookingDto);

        mockMvc.perform(get("/bookings/" + returnBookingDto.getId())
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(returnBookingDto.getId()), Long.class))
                .andExpect(jsonPath("$.item", Matchers.notNullValue()))
                .andExpect(jsonPath("$.booker", Matchers.notNullValue()))
                .andExpect(jsonPath("$.status", Matchers.is(returnBookingDto.getStatus().name())))
                .andExpect(jsonPath("$.start", Matchers.containsString(
                        returnBookingDto.getStart().toString().substring(0, 24))))
                .andExpect(jsonPath("$.end", Matchers.containsString(
                        returnBookingDto.getEnd().toString().substring(0, 24))));

        verify(bookingService, times(1))
                .getBookingById(any(), any());
    }

    @Test
    void getBookingsTest() throws Exception {
        List<ReturnBookingDto> responseBookingDtoList = List.of(returnBookingDto(1L), returnBookingDto(2L));

        when(bookingService.getBookings(any(), any()))
                .thenReturn(responseBookingDtoList);

        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(
                        responseBookingDtoList.get(0).getId()), Long.class))
                .andExpect(jsonPath("$[1].id", Matchers.is(
                        responseBookingDtoList.get(1).getId()), Long.class));

        verify(bookingService, times(1))
                .getBookings(any(), any());
    }

    @Test
    void getAllByOwnerTest() throws Exception {
        List<ReturnBookingDto> returnBookingDtoList = List.of(returnBookingDto(1L), returnBookingDto(2L));

        when(bookingService.getAllByOwner(any(), any()))
                .thenReturn(returnBookingDtoList);

        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(returnBookingDtoList.get(0).getId()), Long.class))
                .andExpect(jsonPath("$[1].id", Matchers.is(returnBookingDtoList.get(1).getId()), Long.class));

        verify(bookingService, times(1))
                .getAllByOwner(any(), any());
    }
}
