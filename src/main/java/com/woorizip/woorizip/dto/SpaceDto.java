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
public class SpaceDto {
    private Long id;
    private String name;

    public static SpaceDto createDto(Space space){
        return SpaceDto.builder().id(space.getId()).name(space.getName()).build();
    }

    public static List<SpaceDto> createDto(List<Space> spaces){
        return spaces.stream().map(SpaceDto::createDto).collect(Collectors.toList());
    }
}
