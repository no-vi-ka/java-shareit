package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> createItemRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                    @RequestBody CreateItemRequestDto itemRequestDto) {
        log.info("Получен запрос на создание запроса на вещь от пользователя с id = {}: ", userId);
        return itemRequestClient.createItemRequest(userId, itemRequestDto);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequestById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long requestId) {
        log.info("Получен запрос данных о запросе на вещь с requestId = {} от пользователя с userId = {}: ", requestId, userId);
        return itemRequestClient.getItemRequestById(requestId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItemRequestForRequestor(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получен запрос на все свои запросы на вещи от владельца с userId = {}: ", userId);
        return itemRequestClient.getAllItemRequestForRequestor(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllItemRequest(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получен запрос на список всех запросов вещей от пользователя с userId = {}: ", userId);
        return itemRequestClient.getAllItemRequest(userId);
    }
}
