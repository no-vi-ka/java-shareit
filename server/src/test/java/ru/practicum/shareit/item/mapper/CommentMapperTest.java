package ru.practicum.shareit.item.mapper;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTest {
    private CommentMapper commentMapper = new CommentMapper();
    private User author;
    private User owner;
    private Item item;

    @BeforeEach
    void setUp() {
        author = User.builder()
                .id(1L)
                .name("Username1")
                .email("mail1@mail.com")
                .build();

        owner = User.builder()
                .id(2L)
                .name("Username2")
                .email("mail1@mail.com")
                .build();

        item = Item.builder()
                .id(1L)
                .name("Itemname")
                .description("Description.")
                .available(true)
                .owner(owner)
                .build();
    }

    @Test
    void toCommentFromCommentDtoTest() {
        CommentDto commentDto = CommentDto.builder()
                .id(1L)
                .authorName("Username1")
                .text("Text")
                .created(LocalDateTime.now())
                .build();

        Comment comment = commentMapper.toCommentFromCommentDto(commentDto);

        assertEquals(commentDto.getId(), comment.getId());
        assertEquals(commentDto.getText(), comment.getText());
        assertEquals(commentDto.getCreated(), comment.getCreated());
    }

    @Test
    void toCommentDtoFromCommentTest() {
        Comment comment = Comment.builder()
                .id(1L)
                .author(author)
                .text("Text.")
                .created(LocalDateTime.now())
                .item(item)
                .build();

        CommentDto commentDto = commentMapper.toCommentDtoFromComment(comment);

        assertEquals(commentDto.getId(), comment.getId());
        assertEquals(commentDto.getText(), comment.getText());
        assertEquals(commentDto.getCreated(), comment.getCreated());
        assertEquals(commentDto.getAuthorName(), comment.getAuthor().getName());
    }
}
