package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;

import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@Valid @RequestBody CreateItemDto itemDto,
                                             @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришёл запрос на создание вещи с name: {}.", itemDto.getName());
        return itemClient.createItem(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@PathVariable("itemId") Long itemId,
                                             @RequestBody UpdateItemDto itemDto,
                                             @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришёл запрос на обновление вещи с id: {}.", itemId);
        return itemClient.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@PathVariable("itemId") Long itemId,
                                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришёл запрос на получение вещи с id: {} от пользователя с id: {}.", itemId, userId);
        return itemClient.getItemById(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsByUserId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Пришёл запрос на получение всех вещей пользователя с id: {}.", userId);
        return itemClient.getItemsByUserId(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(@RequestHeader("X-Sharer-User-Id") Long userId,
                                              @RequestParam(name = "text", required = false) String text) {
        log.info("Пришёл запрос на поиск вещи по описанию: {} от пользователя с id: {}.", text, userId);
        if (text == null || text.trim().isEmpty()) {
            log.info("Пришёл пустой запрос text.");
            return ResponseEntity.ok(Collections.emptyList());
        }
        return itemClient.searchItems(userId, text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestBody @Valid CommentDto commentDto,
                                                @PathVariable Long itemId,
                                                @RequestHeader("X-Sharer-User-Id") @Min(1) Long userId) {
        log.info("Пришел запрос на создание комментария для вещи с id = {}", itemId);
        return itemClient.createComment(userId, itemId, commentDto);
    }
}