package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto addItemRequest(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestBody CreateItemRequestDto itemRequestDto) {
        log.info("Получен запрос на создание запроса на вещь от пользователя с id = {}: ", userId);
        //itemRequestDto.setUserId(userId);
        return itemRequestService.createItemRequest(userId, itemRequestDto);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequestById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable Long requestId) {
        log.info("Получен запрос данных о запросе на вещь с requestId = {} от пользователя с userId = {}: ", requestId, userId);
        return itemRequestService.getItemRequestById(userId, requestId);
    }

    @GetMapping
    public List<ItemRequestDto> getAllItemRequestForRequestor(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получен запрос на все свои запросы на вещи от владельца с userId = {}: ", userId);
        return itemRequestService.getAllItemRequestForRequestor(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAllItemRequest(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Получен запрос на список всех запросов вещей от пользователя с userId = {}: ", userId);
        return itemRequestService.getAllItemRequest(userId);
    }
}