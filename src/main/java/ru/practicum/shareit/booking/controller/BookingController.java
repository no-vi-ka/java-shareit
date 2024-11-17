package ru.practicum.shareit.booking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ReturnBookingDto createBooking(@RequestBody @Valid BookingDto dtoBooking,
                                          @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришёл запрос на создание бронирования для вещи c id = {}.", dtoBooking.getItemId());
        return bookingService.createBooking(dtoBooking, userId);
    }

    @PatchMapping("/{bookingId}")
    public ReturnBookingDto approve(@PathVariable Long bookingId,
                                    @RequestParam(name = "approved") Boolean isApproved,
                                    @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришел запрос на подтверждение бронирования с id = {}", bookingId);
        return bookingService.setApprove(bookingId, isApproved, userId);
    }

    @GetMapping("/{bookingId}")
    public ReturnBookingDto getBookingById(@PathVariable Long bookingId,
                                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришел запрос на получение бронирования с id = {}", bookingId);
        return bookingService.getBookingById(bookingId, userId);
    }

    @GetMapping
    public List<ReturnBookingDto> getAllByBooker(@RequestParam(name = "state", defaultValue = "ALL") String state,
                                                 @RequestHeader("X-Sharer-User-Id") Long bookerId) {
        log.info("Пришел запрос на получения бронирований для пользователя с id = {}", bookerId);
        return bookingService.getAllByBooker(state, bookerId);
    }

    @GetMapping("/owner")
    public List<ReturnBookingDto> getAllByOwner(@RequestParam(name = "state", defaultValue = "ALL") String state,
                                                @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Пришел запрос на подтверждение бронирований владельца с id = {}", ownerId);
        return bookingService.getAllByOwner(state, ownerId);
    }
}