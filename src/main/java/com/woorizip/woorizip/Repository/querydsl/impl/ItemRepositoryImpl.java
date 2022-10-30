package com.woorizip.woorizip.Repository.querydsl.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woorizip.woorizip.Repository.querydsl.ItemRepositoryCustom;
import com.woorizip.woorizip.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.woorizip.woorizip.entity.QCategory.category;
import static com.woorizip.woorizip.entity.QItem.item;
import static com.woorizip.woorizip.entity.QItemCategory.itemCategory;
import static com.woorizip.woorizip.entity.QSpace.space;
import static java.util.stream.Collectors.*;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ItemDto> searchItem(ItemSearchCond searchCond) {
        List<ItemDto> itemDtos = queryFactory
                .select(
                        new QItemDto(
                                item.id,
                                item.name,
                                space.name,
                                item.description)
                )
                .from(item)
                .leftJoin(item.space, space)
                .where(itemNameContains(searchCond.getItemName())
                        , itemDescContains(searchCond.getItemDescription())
                        , spaceNameContains(searchCond.getSpaceName()))
                .orderBy(space.name.asc())
                .fetch();

        return getItemDtoWithCategories(itemDtos, searchCond.getCategoryName());
    }

    @Override
    public List<ExpireItemDto> searchExpireItems(ExpireSearchCond searchCond) {
        return queryFactory
                .select(new QExpireItemDto(
                        item.id
                        , item.name
                        , space.name
                        , item.description
                        , item.expirationDate))
                .from(item)
                .leftJoin(item.space, space)
                .where(beforeExpireDate(searchCond.getExpireDate()))
                .orderBy(item.id.asc())
                .fetch();
    }

    private BooleanExpression itemNameContains(String itemName) {
        return hasText(itemName) ? item.name.contains(itemName) : null;
    }

    private BooleanExpression spaceNameContains(String spaceName) {
        return hasText(spaceName) ? space.name.contains(spaceName) : null;
    }

    private BooleanExpression itemDescContains(String itemDesc) {
        return hasText(itemDesc) ? item.description.contains(itemDesc) : null;
    }

    private List<ItemDto> getItemDtoWithCategories(List<ItemDto> itemDtos, String categoryName) {
        List<Long> itemIds = itemDtos.stream().map(ItemDto::getId).collect(toList());
        Map<Long, List<String>> searchCategories = searchCategories(itemIds, categoryName);
        List<ItemDto> result = new ArrayList<>();
        for (ItemDto itemDto : itemDtos) {
            if (searchCategories.containsKey(itemDto.getId())) {
                itemDto.setCategoryNames(searchCategories.get(itemDto.getId()));
                result.add(itemDto);
            }
        }
        return StringUtils.hasText(categoryName) ? result : itemDtos;
    }

    private Map<Long, List<String>> searchCategories(List<Long> itemIds, String categoryName) {
        List<Tuple> searchCategories = queryFactory
                .select(
                        itemCategory.item.id
                        , category.name)
                .from(item)
                .join(item.itemCategory, itemCategory)
                .join(itemCategory.category, category)
                .where(
                        item.id.in(itemIds)
                        , JPAExpressions
                                .selectOne()
                                .from(itemCategory)
                                .join(itemCategory.category, category)
                                .where(itemCategory.item.id.eq(item.id)
                                        , categoryNameContains(categoryName))
                                .limit(1)
                                .exists()
                )
                .orderBy(itemCategory.item.id.desc())
                .fetch();

        return searchCategories.stream()
                .collect(groupingBy(result -> result.get(itemCategory.item.id)
                        , mapping(result -> result.get(category.name), toList())));
    }

    private BooleanExpression categoryNameContains(String categoryName) {
        return hasText(categoryName) ? category.name.contains(categoryName) : null;
    }

    private BooleanExpression beforeExpireDate(LocalDate expireDate) {
        return item.expirationDate.before(expireDate);
    }

}
