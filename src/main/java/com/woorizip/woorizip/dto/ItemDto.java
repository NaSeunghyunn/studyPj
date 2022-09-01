package com.woorizip.woorizip.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.woorizip.woorizip.entity.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String name;
    private String spaceName;
    private String itemDescription;

    @Builder
    @QueryProjection
    public ItemDto(Long id, String name, String spaceName, String desc) {
        this.id = id;
        this.name = name;
        this.spaceName = spaceName;
        this.itemDescription = desc;
    }

    public static ItemDto createDto(Item item) {
        String spaceName = item.getSpace() == null ? null : item.getSpace().getName();
        return ItemDto.builder().id(item.getId()).name(item.getName()).spaceName(spaceName).desc(item.getDescription()).build();
    }

    public static List<ItemDto> createDto(List<Item> items){
        return items.stream().map(ItemDto::createDto).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto dto = (ItemDto) o;
        return Objects.equals(id, dto.id) && Objects.equals(name, dto.name) && Objects.equals(spaceName, dto.spaceName) && Objects.equals(itemDescription, dto.itemDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, spaceName, itemDescription);
    }
}
