package com.woorizip.woorizip.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryDetailDto {
    private Long id;
    private String name;
    private List<CategoryDto> parents = new ArrayList<>();
}
