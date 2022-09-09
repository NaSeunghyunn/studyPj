package com.woorizip.woorizip.util;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NameCode {
    CATEGORY("category"),
    CATEGORY_NAME("categoryName"),
    PARENT_CATEGORY("parentCategory"),
    SPACE("space"),
    SPACE_NAME("spaceName"),
    PARENT_SPACE("parentSpace");

    private final String code;

    public String code() {
        return code;
    }
}
