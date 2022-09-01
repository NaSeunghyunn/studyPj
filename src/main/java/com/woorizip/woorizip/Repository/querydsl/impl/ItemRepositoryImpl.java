package com.woorizip.woorizip.Repository.querydsl.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woorizip.woorizip.Repository.querydsl.ItemRepositoryCustom;
import com.woorizip.woorizip.dto.ItemDto;
import com.woorizip.woorizip.dto.ItemSearchCond;
import com.woorizip.woorizip.dto.QItemDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.woorizip.woorizip.entity.QItem.item;
import static com.woorizip.woorizip.entity.QSpace.space;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ItemDto> searchItem(ItemSearchCond searchCond) {
        return queryFactory.select(
                        new QItemDto(
                                item.id,
                                item.name,
                                space.name,
                                item.description)
                )
                .from(item)
                .leftJoin(item.space, space)
                .where(itemNameStartsWith(searchCond.getItemName())
                        , itemDescStartWith(searchCond.getItemDescription())
                        , spaceNameStartsWith(searchCond.getSpaceName()))
                .orderBy(space.name.desc())
                .fetch();
    }

    private BooleanExpression itemNameStartsWith(String itemName) {
        return hasText(itemName) ? item.name.contains(itemName) : null;
    }

    private BooleanExpression spaceNameStartsWith(String spaceName) {
        return hasText(spaceName) ? space.name.contains(spaceName) : null;
    }

    private BooleanExpression itemDescStartWith(String itemDesc) {
        return hasText(itemDesc) ? item.description.contains(itemDesc) : null;
    }
}
