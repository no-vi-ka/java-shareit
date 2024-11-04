package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemWithCommentsDto {
        private Long id;
        private String name;
        private String description;
        private boolean available;
        private List<CommentDto> comments;
        private BookingDto lastBooking;
        private BookingDto nextBooking;
    }

