package ru.practicum.shareit.booking.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;
import ru.practicum.shareit.exceptions.ItemIsNotAvailableException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDtoToReturn;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.status.Status;
import ru.practicum.shareit.user.dto.CreateUserDto;
import ru.practicum.shareit.user.dto.ReturnUserDto;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingServiceTest {
    private final ItemRequestService itemRequestService;
    private final UserService userService;
    private final ItemService itemService;
    private final BookingService bookingService;

    private String all = "ALL";
    private String current = "CURRENT";
    private String past = "PAST";
    private String future = "FUTURE";
    private String waiting = "WAITING";
    private String rejected = "REJECTED";
    private String mistake = "Mistake";

    private LocalDateTime start = LocalDateTime.of(2025, 11, 18, 11, 11, 11);
    private LocalDateTime end = LocalDateTime.of(2025, 11, 18, 11, 11, 12);

    private CreateItemDto createItemDto(Long i) {
        return CreateItemDto.builder()
                .name("Itemname" + i)
                .description("Description" + i)
                .available(true)
                .build();
    }

    private CreateItemRequestDto createItemRequestDto(Long i) {
        return CreateItemRequestDto.builder()
                .description("Description" + i)
                .build();
    }

    private CreateUserDto createUserDto(Long i) {
        return CreateUserDto.builder()
                .name("Username" + i)
                .email("mail" + i + "@mail.com")
                .build();
    }

    @Test
    void createBookingTest() {
        CreateUserDto createUserDto1 = createUserDto(1L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateUserDto createUserDto2 = createUserDto(2L);
        ReturnUserDto returnUserDto2 = userService.createUser(createUserDto2);
        CreateUserDto createUserDto3 = createUserDto(3L);
        ReturnUserDto returnUserDto3 = userService.createUser(createUserDto3);
        userService.deleteUser(returnUserDto3.getId());

        CreateItemDto createItemDto1 = createItemDto(1L);
        ItemDtoToReturn itemDtoToReturn1 = itemService.createItem(createItemDto1, returnUserDto1.getId());
        CreateItemDto createItemDto2 = createItemDto(1L);
        ItemDtoToReturn itemDtoToReturn2 = itemService.createItem(createItemDto2, returnUserDto1.getId());
        CreateItemDto createItemDtoNotAvailable = CreateItemDto.builder()
                .name("Itemname")
                .description("Description")
                .available(false)
                .build();
        ItemDtoToReturn itemDtoNotAvailable = itemService.createItem(createItemDtoNotAvailable, returnUserDto1.getId());

        BookingDto bookingDto = BookingDto.builder()
                .start(start)
                .end(end)
                .itemId(itemDtoToReturn1.getId())
                .build();

        BookingDto bookingDto2 = BookingDto.builder()
                .start(start)
                .end(end)
                .itemId(itemDtoToReturn2.getId())
                .build();
        itemService.deleteItem(itemDtoToReturn2, returnUserDto1.getId());
        BookingDto bookingDtoNotAvailable = BookingDto.builder()
                .start(start)
                .end(end)
                .itemId(itemDtoNotAvailable.getId())
                .build();

        assertThrows(NotFoundException.class, () -> bookingService.createBooking(bookingDto, returnUserDto3.getId()));
        assertThrows(NotFoundException.class, () -> bookingService.createBooking(bookingDto2, returnUserDto2.getId()));
        assertThrows(ItemIsNotAvailableException.class, () -> bookingService.createBooking(bookingDtoNotAvailable, returnUserDto2.getId()));

        ReturnBookingDto returnBookingDto = bookingService.createBooking(bookingDto, returnUserDto2.getId());

        assertEquals(returnBookingDto.getItem().getId(), bookingDto.getItemId());
        assertEquals(returnBookingDto.getStart(), bookingDto.getStart());
        assertEquals(returnBookingDto.getEnd(), bookingDto.getEnd());
        assertEquals(returnBookingDto.getBooker().getId(), returnUserDto2.getId());
        assertEquals(returnBookingDto.getStatus(), Status.WAITING);
    }

    @Test
    void updateBookingTest() {
        CreateUserDto createUserDto1 = createUserDto(1L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateUserDto createUserDto2 = createUserDto(2L);
        ReturnUserDto returnUserDto2 = userService.createUser(createUserDto2);
        CreateItemDto createItemDto = createItemDto(1L);
        ItemDtoToReturn itemDtoToReturn = itemService.createItem(createItemDto, returnUserDto1.getId());
        CreateItemDto createItemDto2 = createItemDto(2L);
        ItemDtoToReturn itemDtoToReturn2 = itemService.createItem(createItemDto2, returnUserDto1.getId());

        BookingDto bookingDto = BookingDto.builder()
                .start(start)
                .end(end)
                .itemId(itemDtoToReturn.getId())
                .build();
        ReturnBookingDto returnBookingDto = bookingService.createBooking(bookingDto, returnUserDto2.getId());
        BookingDto bookingDto2 = BookingDto.builder()
                .start(start)
                .end(end)
                .itemId(itemDtoToReturn2.getId())
                .build();
        ReturnBookingDto returnBookingDto2 = bookingService.createBooking(bookingDto2, returnUserDto2.getId());

        assertThrows(NotFoundException.class, () -> bookingService.setApprove(100L, true, returnUserDto1.getId()));
        assertThrows(ValidationException.class, () -> bookingService.setApprove(returnBookingDto.getId(), false, returnUserDto2.getId()));

        ReturnBookingDto notApproved = bookingService.setApprove(returnBookingDto.getId(), false, returnUserDto1.getId());

        assertEquals(notApproved.getStatus(), Status.REJECTED);

        ReturnBookingDto approved = bookingService.setApprove(returnBookingDto2.getId(), true, returnUserDto1.getId());

        assertEquals(approved.getStatus(), Status.APPROVED);

        assertThrows(ItemIsNotAvailableException.class, () -> bookingService.setApprove(returnBookingDto2.getId(), true, returnUserDto1.getId()));
    }

    @Test
    void getBookingByIdTest() {
        CreateUserDto createUserDto1 = createUserDto(1L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateUserDto createUserDto2 = createUserDto(2L);
        ReturnUserDto returnUserDto2 = userService.createUser(createUserDto2);

        CreateItemDto createItemDto1 = createItemDto(1L);
        ItemDtoToReturn itemDtoToReturn1 = itemService.createItem(createItemDto1, returnUserDto1.getId());
        CreateItemDto createItemDto2 = createItemDto(1L);
        ItemDtoToReturn itemDtoToReturn2 = itemService.createItem(createItemDto2, returnUserDto1.getId());

        BookingDto bookingDto = BookingDto.builder()
                .start(start)
                .end(end)
                .itemId(itemDtoToReturn1.getId())
                .build();

        BookingDto bookingDto2 = BookingDto.builder()
                .start(start)
                .end(end)
                .itemId(itemDtoToReturn2.getId())
                .build();
        ReturnBookingDto returnBookingDto = bookingService.createBooking(bookingDto, returnUserDto2.getId());

        ReturnBookingDto finded = bookingService.getBookingById(returnBookingDto.getId(), returnUserDto1.getId());

        assertEquals(finded.getId(), returnBookingDto.getId());
        assertThrows(EntityNotFoundException.class, () -> bookingService.getBookingById(100L, returnUserDto1.getId()));
        assertThrows(ValidationException.class, () -> bookingService.getBookingById(returnBookingDto.getId(), 100L));
    }

    @Test
    void getBookingsTest() {
        CreateUserDto createUserDto1 = createUserDto(1L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateUserDto createUserDto2 = createUserDto(2L);
        ReturnUserDto returnUserDto2 = userService.createUser(createUserDto2);

        CreateItemDto createItemDto1 = createItemDto(1L);
        ItemDtoToReturn itemDtoToReturn1 = itemService.createItem(createItemDto1, returnUserDto1.getId());

        BookingDto bookingDtoCurrent = BookingDto.builder()
                .start(LocalDateTime.of(2020, 11, 18, 11, 11, 11))
                .end(end)
                .itemId(itemDtoToReturn1.getId())
                .build();
        bookingService.createBooking(bookingDtoCurrent, returnUserDto2.getId());

        BookingDto bookingDtoFuture = BookingDto.builder()
                .start(start)
                .end(end)
                .itemId(itemDtoToReturn1.getId())
                .build();
        bookingService.createBooking(bookingDtoFuture, returnUserDto2.getId());

        BookingDto bookingDtoPast = BookingDto.builder()
                .start(LocalDateTime.of(2020, 11, 18, 11, 11, 11))
                .end(LocalDateTime.of(2021, 11, 18, 11, 11, 11))
                .itemId(itemDtoToReturn1.getId())
                .build();
        bookingService.createBooking(bookingDtoPast, returnUserDto2.getId());

        List<ReturnBookingDto> allList = bookingService.getBookings(all, returnUserDto2.getId());
        List<ReturnBookingDto> currentList = bookingService.getBookings(current, returnUserDto2.getId());
        List<ReturnBookingDto> futureList = bookingService.getBookings(future, returnUserDto2.getId());
        List<ReturnBookingDto> pastList = bookingService.getBookings(past, returnUserDto2.getId());
        List<ReturnBookingDto> waitingList = bookingService.getBookings(waiting, returnUserDto2.getId());
        List<ReturnBookingDto> rejectedList = bookingService.getBookings(rejected, returnUserDto2.getId());

        assertEquals(allList.size(), 3);
        assertEquals(currentList.size(), 1);
        assertEquals(futureList.size(), 1);
        assertEquals(pastList.size(), 1);
        assertEquals(waitingList.size(), 3);
        assertEquals(rejectedList.size(), 0);
        assertThrows(NotFoundException.class, () -> bookingService.getBookings(all, 100L));
        assertThrows(ValidationException.class, () -> bookingService.getBookings(mistake, returnUserDto1.getId()));
    }

    @Test
    void getAllByOwnerTest() {
        CreateUserDto createUserDto1 = createUserDto(1L);
        ReturnUserDto returnUserDto1 = userService.createUser(createUserDto1);
        CreateUserDto createUserDto2 = createUserDto(2L);
        ReturnUserDto returnUserDto2 = userService.createUser(createUserDto2);

        CreateItemDto createItemDto1 = createItemDto(1L);
        ItemDtoToReturn itemDtoToReturn1 = itemService.createItem(createItemDto1, returnUserDto1.getId());

        BookingDto bookingDtoCurrent = BookingDto.builder()
                .start(LocalDateTime.of(2020, 11, 18, 11, 11, 11))
                .end(end)
                .itemId(itemDtoToReturn1.getId())
                .build();
        bookingService.createBooking(bookingDtoCurrent, returnUserDto2.getId());

        BookingDto bookingDtoFuture = BookingDto.builder()
                .start(start)
                .end(end)
                .itemId(itemDtoToReturn1.getId())
                .build();
        bookingService.createBooking(bookingDtoFuture, returnUserDto2.getId());

        BookingDto bookingDtoPast = BookingDto.builder()
                .start(LocalDateTime.of(2020, 11, 18, 11, 11, 11))
                .end(LocalDateTime.of(2021, 11, 18, 11, 11, 11))
                .itemId(itemDtoToReturn1.getId())
                .build();
        bookingService.createBooking(bookingDtoPast, returnUserDto2.getId());

        List<ReturnBookingDto> allList = bookingService.getAllByOwner(all, returnUserDto1.getId());
        List<ReturnBookingDto> currentList = bookingService.getAllByOwner(current, returnUserDto1.getId());
        List<ReturnBookingDto> futureList = bookingService.getAllByOwner(future, returnUserDto1.getId());
        List<ReturnBookingDto> pastList = bookingService.getAllByOwner(past, returnUserDto1.getId());
        List<ReturnBookingDto> waitingList = bookingService.getAllByOwner(waiting, returnUserDto1.getId());
        List<ReturnBookingDto> rejectedList = bookingService.getAllByOwner(rejected, returnUserDto1.getId());

        assertEquals(allList.size(), 3);
        assertEquals(currentList.size(), 1);
        assertEquals(futureList.size(), 1);
        assertEquals(pastList.size(), 1);
        assertEquals(waitingList.size(), 3);
        assertEquals(rejectedList.size(), 0);
        assertThrows(NotFoundException.class, () -> bookingService.getAllByOwner(all, 100L));
        assertThrows(ValidationException.class, () -> bookingService.getAllByOwner(mistake, returnUserDto1.getId()));
    }
}
