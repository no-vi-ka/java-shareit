package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item createItem(CreateItemDto itemDto, Integer userId) {
        return itemRepository.createItem(itemDto, userId);
    }

    public Item updateItem(UpdateItemDto itemDto, Integer userId, Integer itemId) {
        return itemRepository.updateItem(itemDto, userId, itemId);
    }

    public Item getItemById(Integer itemId) {
        return itemRepository.getItemById(itemId);
    }

    public List<Item> getItemsByUserId(Integer userId) {
        return itemRepository.getItemsByUserId(userId);
    }

    public void deleteItem(Item item, Integer userId) {
        itemRepository.deleteItem(item, userId);
    }

    public List<Item> searchItems(Integer userId, String text) {
        return itemRepository.searchItems(userId, text);
    }
}