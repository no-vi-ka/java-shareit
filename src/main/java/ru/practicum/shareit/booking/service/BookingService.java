package ru.practicum.shareit.booking.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.ItemIsNotAvailableException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.state.State;
import ru.practicum.shareit.status.Status;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ReturnBookingDto createBooking(@Valid BookingDto dtoBooking, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id = " + userId + " is not found."));
        Item item = itemRepository.findById(dtoBooking.getItemId()).orElseThrow(
                () -> new NotFoundException("Item with id = " + dtoBooking.getItemId() + " is not found."));
        if (!item.getAvailable()) {
            throw new ItemIsNotAvailableException("Item с id = " + item.getId() + " не доступен для бронирования.");
        }
        Booking created = bookingMapper.toBookingFromBookingDto(dtoBooking);
        created.setBooker(user);
        created.setItem(item);
        created.setStatus(Status.WAITING);
        return bookingMapper.toReturnBookingDto(bookingRepository.save(created));
    }

    public ReturnBookingDto setApprove(Integer bookingId, Boolean isApproved, Integer userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Booking with id = " + bookingId + " is not found."));
        if (!booking.getStatus().equals(Status.WAITING)) {
            throw new ItemIsNotAvailableException("Item with id = " + booking.getItem().getId()
                    + " already in use.");
        }
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new ValidationException("User with id = " + userId
                    + " is not owner and can not set status for booking.");
        }
        if (isApproved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        return bookingMapper.toReturnBookingDto(bookingRepository.save(booking));
    }

    public ReturnBookingDto getBookingById(Integer bookingId, Integer userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new EntityNotFoundException("Бронирование не найдено"));
        if (!booking.getBooker().getId().equals(userId) && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new ValidationException("User with id = " + userId
                    + " is not owner or booker and can not view this booking.");
        }
        return bookingMapper.toReturnBookingDto(booking);
    }


    public List<ReturnBookingDto> getAllByBooker(String state, Integer bookerId) {
        if (!userRepository.existsById(bookerId)) {
            throw new NotFoundException("User with id = " + bookerId + " is not found.");
        }
        State stateFromString;
        try {
            stateFromString = State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("State " + state + " is not support");
        }
        return switch (stateFromString) {
            case ALL -> bookingRepository.findAllByBookerId(bookerId).stream()
                    .map(bookingMapper::toReturnBookingDto)
                    .toList();
            case CURRENT -> bookingRepository.findByBookerAndStateCurrent(bookerId).stream()
                    .map(bookingMapper::toReturnBookingDto)
                    .toList();
            case PAST -> bookingRepository.findByBookerAndStatePast(bookerId).stream()
                    .map(bookingMapper::toReturnBookingDto)
                    .toList();
            case FUTURE -> bookingRepository.findByBookerAndStateFuture(bookerId).stream()
                    .map(bookingMapper::toReturnBookingDto)
                    .toList();
            case WAITING -> bookingRepository.findAllByBookerIdAndStatus(bookerId, Status.WAITING).stream()
                    .map(bookingMapper::toReturnBookingDto)
                    .toList();
            case REJECTED -> bookingRepository.findAllByBookerIdAndStatus(bookerId, Status.REJECTED).stream()
                    .map(bookingMapper::toReturnBookingDto)
                    .toList();
        };
    }

    public List<ReturnBookingDto> getAllByOwner(String state, Integer ownerId) {
        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException("User with id = " + ownerId + " is not found.");
        }
        State stateFromString;
        try {
            stateFromString = State.valueOf(state);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("State " + state + " is not support");
        }
        return switch (stateFromString) {
            case ALL -> bookingRepository.findAllByOwnerId(ownerId).stream()
                    .map(bookingMapper::toReturnBookingDto)
                    .toList();
            case CURRENT -> bookingRepository.findByOwnerAndStateCurrent(ownerId).stream()
                    .map(bookingMapper::toReturnBookingDto)
                    .toList();
            case PAST -> bookingRepository.findByOwnerAndStatePast(ownerId).stream()
                    .map(bookingMapper::toReturnBookingDto)
                    .toList();
            case FUTURE -> bookingRepository.findByOwnerAndStateFuture(ownerId).stream()
                    .map(bookingMapper::toReturnBookingDto)
                    .toList();
            case WAITING -> bookingRepository.findAllByOwnerIdAndStatus(ownerId, Status.WAITING).stream()
                    .map(bookingMapper::toReturnBookingDto)
                    .toList();
            case REJECTED -> bookingRepository.findAllByOwnerIdAndStatus(ownerId, Status.REJECTED).stream()
                    .map(bookingMapper::toReturnBookingDto)
                    .toList();
        };
    }
}
