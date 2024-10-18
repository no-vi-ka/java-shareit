package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.NotOwnerException;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final ItemMapper itemMapper;
    private final UserRepository userRepository;

    private Integer id = 0;
    private Map<Integer, Item> items = new HashMap<>();

    public Item createItem(CreateItemDto itemDto, Integer userId) {
        userRepository.checkContainsUserById(userId);
        Item item = itemMapper.toItemFromCreateDto(itemDto);
        item.setId(++id);
        item.setOwner(userId);
        items.put(id, item);
        log.info("Создана вещь с id: {}.", id);
        return item;
    }

    public Item updateItem(UpdateItemDto itemDto, Integer userId, Integer itemId) {
        Item item = getItemById(itemId);
        userRepository.checkContainsUserById(userId);
        checkBelongingToOwner(item, userId);
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        items.put(itemId, item);
        log.info("Обновлена вещь с id: {}.", itemId);
        return item;
    }

    public Item getItemById(Integer itemId) {
        checkContainsItemById(itemId);
        log.info("Получена вещь с id: {}.", itemId);
        return Optional.ofNullable(items.get(itemId)).orElseThrow(() -> new NotFoundException("Вещь с id = " + itemId + " не найдена."));
    }

    public List<Item> getItemsByUserId(Integer userId) {
        userRepository.checkContainsUserById(userId);
        log.info("Получен список всех вещей пользователя с id: {}.", userId);
        return items.values().stream().filter(item -> item.getOwner().equals(userId)).collect(Collectors.toList());
    }

    public void deleteItem(Item item, Integer userId) {
        checkContainsItemById(item.getId());
        userRepository.checkContainsUserById(userId);
        checkBelongingToOwner(item, userId);
        items.remove(item.getId());
        log.info("Удалена вещь с id: {}.", item.getId());
    }

    public List<Item> searchItems(Integer userId, String text) {
        if (text.isEmpty() || text.isBlank()) {
            return Collections.emptyList();
        }
        userRepository.checkContainsUserById(userId);
        log.info("Получен список вещей по описанию: {} от пользователя с id: {}.", text, userId);
        return items.values().stream()
                .filter(item -> item.getAvailable())
                .filter(i -> ((i.getName().toLowerCase().contains(text.toLowerCase())) || (i.getDescription().toLowerCase().contains(text.toLowerCase()))))
                .collect(Collectors.toList());
    }

    private void checkContainsItemById(Integer id) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("Вещь с id = " + id + " не найдена.");
        }
    }

    private void checkBelongingToOwner(Item item, Integer userId) {
        if (!item.getOwner().equals(userId)) {
            throw new NotOwnerException("Пользователь с id = " + id + " не является владельцем вещи.");
        }
    }
}