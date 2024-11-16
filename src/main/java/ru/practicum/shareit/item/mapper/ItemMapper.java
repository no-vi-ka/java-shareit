package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.item.dto.CreateItemDto;
import ru.practicum.shareit.item.dto.ItemDtoToReturn;
import ru.practicum.shareit.item.dto.ItemWithCommentsDto;
import ru.practicum.shareit.item.dto.UpdateItemDto;
import ru.practicum.shareit.item.model.Item;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {
    Item toItemFromCreateDto(CreateItemDto itemDto);

    Item toItemFromUpdateDto(UpdateItemDto itemDto);

    Item toItemFromItemWithCommentsDto(ItemWithCommentsDto itemWithCommentsDto);

    Item toItemFromItemDtoToReturn(ItemDtoToReturn itemDto);

    CreateItemDto toCreateItemDto(Item item);

    UpdateItemDto toUpdateItemDto(Item item);

    ItemWithCommentsDto toItemWithCommentsDtoFromItem(Item item);

    @Mapping(target = "owner", source = "item.owner")
    ItemDtoToReturn toItemDtoToReturn(Item item);
}