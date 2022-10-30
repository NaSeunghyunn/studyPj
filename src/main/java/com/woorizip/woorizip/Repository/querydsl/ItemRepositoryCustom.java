package com.woorizip.woorizip.Repository.querydsl;

import com.woorizip.woorizip.dto.ExpireItemDto;
import com.woorizip.woorizip.dto.ExpireSearchCond;
import com.woorizip.woorizip.dto.ItemDto;
import com.woorizip.woorizip.dto.ItemSearchCond;

import java.util.List;

public interface ItemRepositoryCustom {
    List<ItemDto> searchItem(ItemSearchCond searchCond);
    List<ExpireItemDto> searchExpireItems(ExpireSearchCond searchCond);
}
