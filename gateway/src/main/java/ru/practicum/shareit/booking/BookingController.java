package ru.practicum.shareit.booking;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingState;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
	private final BookingClient bookingClient;

	@PostMapping
	public ResponseEntity<Object> createBooking(@RequestBody @Valid BookingDto dtoBooking,
												@RequestHeader("X-Sharer-User-Id") Long userId) {
		log.info("Пришёл запрос на создание бронирования для вещи c id = {}.", dtoBooking.getItemId());
		return bookingClient.createBooking(userId, dtoBooking);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> approve(@PathVariable Long bookingId,
										  @RequestParam Boolean approved,
										  @RequestHeader("X-Sharer-User-Id") Long userId) {
		log.info("Пришел запрос на подтверждение бронирования с id = {}", bookingId);
		return bookingClient.approve(userId, bookingId, approved);
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<Object> getBookingById(@PathVariable Long bookingId,
												 @RequestHeader("X-Sharer-User-Id") Long userId) {
		log.info("Пришел запрос на получение бронирования с id = {}", bookingId);
		return bookingClient.getBookingById(bookingId, userId);
	}

	@GetMapping
	public ResponseEntity<Object> getBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
											  @RequestParam(name = "state", defaultValue = "all") String stateParam,
											  @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
											  @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
		BookingState state = BookingState.from(stateParam)
				.orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
		log.info("Get booking with state {}, userId={}, from={}, size={}", stateParam, userId, from, size);
		return bookingClient.getBookings(userId, state, from, size);
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> getAllByOwner(@RequestParam(name = "state", defaultValue = "ALL") String state,
												@RequestHeader("X-Sharer-User-Id") Long ownerId) {
		log.info("Пришел запрос на подтверждение бронирований владельца с id = {}", ownerId);
		return bookingClient.getAllByOwner(ownerId, state);
	}
}
