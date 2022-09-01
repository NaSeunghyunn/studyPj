package com.woorizip.woorizip.dto;

import com.woorizip.woorizip.entity.Space;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class ItemSpaceDto {
    private Long id;
    private String name;

    public static ItemSpaceDto createDto(Space space){
        return ItemSpaceDto.builder().id(space.getId()).name(space.getName()).build();
    }

    public static List<ItemSpaceDto> createDto(List<Space> spaces){
        return spaces.stream().map(ItemSpaceDto::createDto).collect(Collectors.toList());
    }
}
