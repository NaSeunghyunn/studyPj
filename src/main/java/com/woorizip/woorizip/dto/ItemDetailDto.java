package com.woorizip.woorizip.dto;

import com.woorizip.woorizip.entity.Category;
import com.woorizip.woorizip.entity.Item;
import com.woorizip.woorizip.entity.Space;
import com.woorizip.woorizip.util.DateUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemDetailDto {
    private Long id;
    private String name;
    private String purchaseDate;
    private String expirationDate;
    private String disposeFlg;
    private String disappearFlg;
    private String description;
    private List<ItemSpaceDto> spaces = new ArrayList<>();
    private List<ItemCategoryDto> categories = new ArrayList<>();

    @Builder
    public ItemDetailDto(Long id, String name, String purchaseDate, String expirationDate, String disposeFlg, String disappearFlg, String description, List<ItemCategoryDto> categories) {
        this.id = id;
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.expirationDate = expirationDate;
        this.disposeFlg = disposeFlg;
        this.disappearFlg = disappearFlg;
        this.description = description;
        this.categories = categories;
    }

    public ItemDetailDto(Long id, String name, String purchaseDate, String expirationDate, String disposeFlg, String disappearFlg, String description) {
        this.id = id;
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.expirationDate = expirationDate;
        this.disposeFlg = disposeFlg;
        this.disappearFlg = disappearFlg;
        this.description = description;
    }

    public static ItemDetailDto createDto(Item item, List<Space> space, List<Category> categories){
        ItemDetailDto itemDetailDto = createDto(item);
        itemDetailDto.setSpaces(ItemSpaceDto.createDto(space));
        itemDetailDto.setCategories(ItemCategoryDto.createDto(categories));
        return itemDetailDto;
    }

    public static ItemDetailDto createDto(Item item) {
        return ItemDetailDto.builder()
                .id(item.getId())
                .name(item.getName())
                .purchaseDate(DateUtil.formatDate(item.getPurchaseDate()))
                .expirationDate(DateUtil.formatDate(item.getExpirationDate()))
                .disposeFlg(item.getDisposeFlg())
                .disappearFlg(item.getDisappearFlg())
                .description(item.getDescription()).build();
    }
}
