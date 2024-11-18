package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.NotOwnerException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.status.Status;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ItemRequestRepository itemRequestRepository;

    public ItemDtoToReturn createItem(CreateItemDto itemDto, Long userId) {
        Item itemFromDto = itemMapper.toItemFromCreateDto(itemDto);
        itemFromDto.setOwner(userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id = " + userId + " not found.")));
        if (itemDto.getRequestId() != null) {
            ItemRequest request = itemRequestRepository.findById(itemDto.getRequestId())
                    .orElseThrow(() -> new NotFoundException("Запрос с id = "
                            + itemDto.getRequestId() + " не найден."));
            itemFromDto.setRequest(request);
        }
        log.info("Item was created.");
        return itemMapper.toItemDtoToReturn(itemRepository.save(itemFromDto));
    }

    public ItemDtoToReturn updateItem(UpdateItemDto itemDto, Long userId, Long itemId) {
        Item itemFromTable = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item with id = " + itemId + " not found."));
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id = " + userId + " is not found.");
        }
        if (!itemFromTable.getOwner().getId().equals(userId)) {
            throw new NotOwnerException("User with id = " + userId + " is not owner for this item.");
        }
        String name = itemDto.getName();
        if (name != null && !name.isBlank()) {
            itemFromTable.setName(name);
        }
        String description = itemDto.getDescription();
        if (description != null && !description.isBlank()) {
            itemFromTable.setDescription(description);
        }
        Boolean available = itemDto.getAvailable();
        if (available != null) {
            itemFromTable.setAvailable(available);
        }
        log.info("Item with id = " + itemId + " was updated.");
        return itemMapper.toItemDtoToReturn(itemRepository.save(itemFromTable));
    }

    public ItemWithCommentsDto getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item with id = " + itemId + " not found."));
        ItemWithCommentsDto itemWithCommentsDto = itemMapper.toItemWithCommentsDtoFromItem(item);
        List<CommentDto> commentDtoList = commentRepository.findAllByItemId(itemId).stream().map(commentMapper::toCommentDtoFromComment).toList();
        itemWithCommentsDto.setComments(commentDtoList);
        log.info("Get Item with comments with id = {}", itemId);
        return itemWithCommentsDto;
    }

    public List<ItemDtoToReturn> getItemsByUserId(Long userId) {
        return itemRepository.findAllByOwnerId(userId).stream().map(itemMapper::toItemDtoToReturn).toList();
    }

    public void deleteItem(ItemDtoToReturn item, Long userId) {
        if (!itemRepository.existsById(item.getId())) {
            throw new NotFoundException("Item with id = " + item.getId() + " not found.");
        }
        if (!item.getOwner().getId().equals(userId)) {
            throw new NotOwnerException("User with id = " + userId + " is not owner for this item.");
        }
        itemRepository.deleteById(item.getId());
        log.info("Delete Item with id = {}", item.getId());
    }

    public List<ItemDtoToReturn> searchItems(Long userId, String text) {
        if (text.isEmpty() || text.isBlank()) {
            return Collections.emptyList();
        }
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id = " + userId + " not found.");
        }
        log.info("Search for Items by text {} was completed.", text);
        return itemRepository.search(text).stream()
                .filter(Item::getAvailable)
                .map(itemMapper::toItemDtoToReturn)
                .toList();
    }

    public CommentDto createComment(Long userId, Long itemId, CommentDto commentDto) {
        Item item = itemRepository.findById(itemId).orElseThrow(
                () -> new NotFoundException("Item with id = " + itemId + " not found."));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id = " + userId + " not found."));
        Comment comment = commentMapper.toCommentFromCommentDto(commentDto);
        if (bookingRepository.findAllByBookerIdAndItemIdAndStatusEqualsAndEndIsBefore(userId, itemId, Status.APPROVED,
                LocalDateTime.now()).isEmpty()) {
            throw new NotOwnerException("User with id = " + userId + " did not book item with id = " + itemId);
        }

        comment.setAuthor(user);
        comment.setItem(item);
        comment.setCreated(LocalDateTime.now());
        log.info("Comment with id = {} was created.", comment.getId());
        return commentMapper.toCommentDtoFromComment(commentRepository.save(comment));
    }
}