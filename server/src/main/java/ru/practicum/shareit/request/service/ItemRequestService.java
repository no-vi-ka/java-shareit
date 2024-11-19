package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDtoForRequest;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemRequestService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserRepository userRepository;
    private final ItemRequestMapper itemRequestMapper;
    private final ItemRequestRepository itemRequestRepository;

    public ItemRequestDto createItemRequest(Long userId, CreateItemRequestDto itemRequestDto) {
        User requestor = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id = " + userId + " not found."));
        ItemRequest itemRequest = itemRequestMapper.toItemRequestFromCreateDto(itemRequestDto);
        itemRequest.setRequestor(requestor);
        itemRequest.setCreated(LocalDateTime.now());
        List<ItemDtoForRequest> items = Collections.emptyList();
        log.info("Создан запрос на вещь с requestorId = {}", requestor.getId());
        return itemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest));
    }

    public ItemRequestDto getItemRequestById(Long userId, Long requestId) {
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Не найден запрос на вещь с id = " + requestId));
        List<Item> itemList = itemRepository.findAllByRequestId(requestId);
        List<ItemDtoForRequest> itemForItemRequestDtoList = itemList.stream().map(itemMapper::toItemDtoForRequest).toList();
        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDtoWithItemsList(itemRequest, itemForItemRequestDtoList);
        return itemRequestDto;
    }

    public List<ItemRequestDto> getAllItemRequestForRequestor(Long userId) {
        User requestor = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id = " + userId + " not found."));
        List<ItemRequest> requests = itemRequestRepository.findByRequestorIdOrderByCreatedDesc(userId);
        if (requests == null) {
            return Collections.emptyList();
        }
            return requests.stream().map(itemRequestMapper::toItemRequestDto).toList();
    }


    public List<ItemRequestDto> getAllItemRequest(Long userId) {
        User requestor = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User with id = " + userId + " not found."));
        List<ItemRequest> requests = itemRequestRepository.findByOtherUsers(userId);
        if (requests == null) {
            return Collections.emptyList();
        }
        return requests.stream().map(itemRequestMapper::toItemRequestDto).toList();
        }
    }
