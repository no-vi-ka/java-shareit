package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.ReturnBookingDto;
import ru.practicum.shareit.booking.model.Booking;

import java.awt.print.Book;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {
    Booking toBookingFromBookingDto(BookingDto bookingDto);

    Booking toBookingFromReturnBookingDto(ReturnBookingDto bookingDto);

    BookingDto toBookingDtoFromBooking(Booking booking);

    ReturnBookingDto toReturnBookingDto(Booking booking);
}
