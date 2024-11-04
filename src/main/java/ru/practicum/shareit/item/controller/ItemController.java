package ru.practicum.shareit.item.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemWithCommentsDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Item createItem(@Valid @RequestBody CreateItemDto itemDto,
                           @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришёл запрос на создание вещи с name: {}.", itemDto.getName());
        return itemService.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@PathVariable("itemId") Integer itemId,
                           @RequestBody UpdateItemDto itemDto,
                           @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришёл запрос на обновление вещи с id: {}.", itemId);
        return itemService.updateItem(itemDto, userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemWithCommentsDto getItemById(@PathVariable("itemId") Integer itemId,
                                           @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришёл запрос на получение вещи с id: {} от пользователя с id: {}.", itemId, userId);
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<Item> getItemsByUserId(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришёл запрос на получение всех вещей пользователя с id: {}.", userId);
        return itemService.getItemsByUserId(userId);
    }

    @DeleteMapping
    public void deleteItem(@RequestBody Item item,
                           @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришёл запрос на удаление вещи с id: {} от пользователя с id: {}.", item.getId(), userId);
        itemService.deleteItem(item, userId);
    }

    @GetMapping("/search")
    public List<Item> searchItems(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                  @RequestParam(name = "text", required = false) String text) {
        log.info("Пришёл запрос на поиск вещи по описанию: {} от пользователя с id: {}.", text, userId);
        return itemService.searchItems(userId, text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createCommentDto(@RequestBody @Valid CommentDto commentDto,
                                       @PathVariable Integer itemId,
                                       @RequestHeader("X-Sharer-User-Id") @Min(1) Integer userId) {
        log.info("Пришел запрос на создание комментария для вещи с id = {}", itemId);
        return itemService.createComment(userId, itemId, commentDto);
    }
}