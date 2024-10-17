package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item toItemFromCreateDto(CreateItemDto itemDto);

    Item toItemFromUpdateDto(UpdateItemDto itemDto);

    CreateItemDto toCreateItemDto(Item item);

    UpdateItemDto toUpdateItemDto(Item item);
}