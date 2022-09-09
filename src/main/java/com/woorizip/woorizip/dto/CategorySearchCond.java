package com.woorizip.woorizip.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class CategorySearchCond {
    private List<Long> itemIds = new ArrayList<>();
    private String categoryName;
}
