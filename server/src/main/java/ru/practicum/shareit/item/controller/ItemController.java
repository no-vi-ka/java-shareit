package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDtoToReturn createItem(@RequestBody CreateItemDto itemDto,
                                      @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришёл запрос на создание вещи с name: {}.", itemDto.getName());
        return itemService.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDtoToReturn updateItem(@PathVariable("itemId") Long itemId,
                                      @RequestBody UpdateItemDto itemDto,
                                      @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришёл запрос на обновление вещи с id: {}.", itemId);
        return itemService.updateItem(itemDto, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemWithCommentsDto getItemById(@PathVariable("itemId") Long itemId,
                                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришёл запрос на получение вещи с id: {} от пользователя с id: {}.", itemId, userId);
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<ItemDtoToReturn> getItemsByUserId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришёл запрос на получение всех вещей пользователя с id: {}.", userId);
        return itemService.getItemsByUserId(userId);
    }

    @DeleteMapping
    public void deleteItem(@RequestBody ItemDtoToReturn item,
                           @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришёл запрос на удаление вещи с id: {} от пользователя с id: {}.", item.getId(), userId);
        itemService.deleteItem(item, userId);
    }

    @GetMapping("/search")
    public List<ItemDtoToReturn> searchItems(@RequestHeader("X-Sharer-User-Id") Long userId,
                                             @RequestParam(name = "text", required = false) String text) {
        log.info("Пришёл запрос на поиск вещи по описанию: {} от пользователя с id: {}.", text, userId);
        return itemService.searchItems(userId, text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createCommentDto(@RequestBody CommentDto commentDto,
                                       @PathVariable Long itemId,
                                       @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришел запрос на создание комментария для вещи с id = {}", itemId);
        return itemService.createComment(userId, itemId, commentDto);
    }
}