package com.woorizip.woorizip.dto;

import com.woorizip.woorizip.controller.form.ItemForm;
import com.woorizip.woorizip.controller.form.ItemSearchForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ItemSearchCond {
    private String itemName;
    private String itemDescription;
    private String spaceName;
    private String categoryName;

    public static ItemSearchCond of(ItemForm form) {
        return ItemSearchCond.builder()
                .itemName(form.getItemName())
                .itemDescription(form.getItemDescription())
                .spaceName(form.getSpaceName())
                .categoryName(form.getCategoryName()).build();
    }
}

