package com.woorizip.woorizip.dto;

import com.woorizip.woorizip.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class ItemCategoryDto {
    private Long id;
    private String name;

    public static ItemCategoryDto createDto(Category category){
        return ItemCategoryDto.builder().id(category.getId()).name(category.getName()).build();
    }

    public static List<ItemCategoryDto> createDto(List<Category> categories){
        return categories.stream().map(ItemCategoryDto::createDto).collect(Collectors.toList());
    }
}
