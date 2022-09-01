package com.woorizip.woorizip.Repository.querydsl;

import com.woorizip.woorizip.dto.ItemDto;
import com.woorizip.woorizip.dto.ItemSearchCond;

import java.util.List;

public interface ItemRepositoryCustom {
    List<ItemDto> searchItem(ItemSearchCond searchCond);
}
