package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemMapper {
    Item toItemFromCreateDto(CreateItemDto itemDto);

    @Mapping(target = "requestId", source = "item.request.id")
    ItemWithCommentsDto toItemWithCommentsDtoFromItem(Item item);

    @Mapping(target = "owner", source = "item.owner")
    @Mapping(target = "requestId", source = "item.request.id")
    ItemDtoToReturn toItemDtoToReturn(Item item);

    ItemDtoForRequest toItemDtoForRequest(Item item);
}