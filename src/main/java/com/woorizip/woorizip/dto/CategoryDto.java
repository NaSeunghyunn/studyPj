package com.woorizip.woorizip.dto;

import com.woorizip.woorizip.entity.Category;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CategoryDto {
    private Long id;
    private String name;

    public static CategoryDto createDto(Category category) {
        return CategoryDto.builder().id(category.getId()).name(category.getName()).build();
    }

    public static List<CategoryDto> createDto(List<Category> categories) {
        return categories.stream().map(CategoryDto::createDto).collect(Collectors.toList());
    }
}
