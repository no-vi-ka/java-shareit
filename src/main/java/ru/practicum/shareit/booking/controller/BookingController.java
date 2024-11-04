package ru.practicum.shareit.booking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.model.User;

import java.awt.print.Book;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public Booking createBooking(@RequestBody @Valid BookingDto dtoBooking, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришёл запрос на создание бронирования для вещи c id = {}.", dtoBooking.getItemId());
        return bookingService.createBooking(dtoBooking, userId);
    }

    @PatchMapping("/{bookingId}")
    public Booking approve(@PathVariable Integer bookingId,
                           @RequestParam(name = "approved") Boolean isApproved,
                           @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришел запрос на подтверждение бронирования с id = {}", bookingId);
        return bookingService.setApprove(bookingId, isApproved, userId);
    }

    @GetMapping("/{bookingId}")
    public Booking getBookingById(@PathVariable Integer bookingId,
                                  @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришел запрос на получение бронирования с id = {}", bookingId);
        return bookingService.getBookingById(bookingId, userId);
    }

    @GetMapping
    public List<Booking> getAllByBooker(@RequestParam(name = "state", defaultValue = "ALL") String state,
                                        @RequestHeader("X-Sharer-User-Id") Integer bookerId) {
        log.info("Пришел запрос на получения бронирований для пользователя с id = {}", bookerId);
        return bookingService.getAllByBooker(state, bookerId);
    }

    @GetMapping("/owner")
    public List<Booking> getAllByOwner(@RequestParam(name = "state", defaultValue = "ALL") String state,
                                       @RequestHeader("X-Sharer-User-Id") Integer ownerId) {
        log.info("Пришел запрос на подтверждение бронирований владельца с id = {}", ownerId);
        return bookingService.getAllByOwner(state, ownerId);
    }
}