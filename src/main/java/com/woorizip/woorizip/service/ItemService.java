package com.woorizip.woorizip.service;

import com.woorizip.woorizip.Repository.CategoryRepository;
import com.woorizip.woorizip.Repository.ItemCategoryRepository;
import com.woorizip.woorizip.Repository.ItemRepository;
import com.woorizip.woorizip.Repository.SpaceRepository;
import com.woorizip.woorizip.controller.form.ItemSaveForm;
import com.woorizip.woorizip.dto.ItemDetailDto;
import com.woorizip.woorizip.dto.ItemDto;
import com.woorizip.woorizip.dto.ItemSearchCond;
import com.woorizip.woorizip.entity.Category;
import com.woorizip.woorizip.entity.Item;
import com.woorizip.woorizip.entity.ItemCategory;
import com.woorizip.woorizip.entity.Space;
import com.woorizip.woorizip.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.woorizip.woorizip.util.MessageCode.*;
import static com.woorizip.woorizip.util.NameCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SpaceRepository spaceRepository;

    public ItemDetailDto getItem(Long id) {
        existItem(id);
        Item item = itemRepository.getReferenceById(id);
        List<Space> spaces = getSpaces(item.getSpace());
        List<Category> categories = itemCategoryRepository.findFetchJoinByItem(item).stream()
                .map(ItemCategory::getCategory).collect(Collectors.toList());
        return ItemDetailDto.createDto(item, spaces, categories);
    }

    private List<Space> getSpaces(Space space) {
        List<Space> spaces = new ArrayList<>();
        if (space != null) {
            List<Long> spaceIds = spaceRepository.findParentIds(space.getId());
            spaces = spaceRepository.findByIdIn(spaceIds);
        }
        return spaces;
    }

    private void existItem(Long id) {
        boolean existItem = itemRepository.existsById(id);
        if (!existItem) {
            throw new ApiException(NOT_EXIST_ERROR);
        }
    }

    public Long saveItem(ItemSaveForm form) {
        Item saveItem = saveOrUpdateByExist(form);
        modifyItemCategories(form, saveItem);

        return saveItem.getId();
    }

    private void modifyItemCategories(ItemSaveForm form, Item saveItem) {
        List<ItemCategory> itemCategories = itemCategoryRepository.findFetchJoinByItem(saveItem).stream()
                .collect(Collectors.toList());

        deleteItemCategories(form, itemCategories);
        saveItemCategories(form, saveItem, itemCategories);
    }

    private void saveItemCategories(ItemSaveForm form, Item saveItem, List<ItemCategory> itemCategories) {
        List<ItemCategory> itemCategoriesForSave = getItemCategoriesForSave(form, itemCategories, saveItem);
        itemCategoryRepository.saveAll(itemCategoriesForSave);
    }

    private List<ItemCategory> getItemCategoriesForSave(ItemSaveForm form, List<ItemCategory> itemCategories, Item saveItem) {
        List<Long> oldCategoryIds = itemCategories.stream()
                .map(ItemCategory :: getCategory)
                .map(Category :: getId).collect(Collectors.toList());

        List<ItemCategory> itemCategoriesForSave = new ArrayList<>();
        for(Long inputCategoryId : form.getCategoryIds()){
            if(!oldCategoryIds.contains(inputCategoryId)){
                existCategory(inputCategoryId);
                Category saveCategory = categoryRepository.getReferenceById(inputCategoryId);
                ItemCategory itemCategory = ItemCategory.builder().item(saveItem).category(saveCategory).build();
                itemCategoriesForSave.add(itemCategory);
            }
        }
        return itemCategoriesForSave;
    }

    private void existCategory(Long inputCategoryId) {
        boolean existCategory = categoryRepository.existsById(inputCategoryId);
        if(!existCategory){
            throw new ApiException(NOT_EXIST_DATA_ERROR, CATEGORY.code());
        }
    }

    private void deleteItemCategories(ItemSaveForm form, List<ItemCategory> itemCategories) {
        List<ItemCategory> deleteTargets = itemCategories.stream()
                .filter(itemCategory -> !form.getCategoryIds().contains(itemCategory.getCategory().getId()))
                .collect(Collectors.toList());
        itemCategoryRepository.deleteAllInBatch(deleteTargets);
    }

    private Item saveOrUpdateByExist(ItemSaveForm form) {
        if (itemRepository.existsById(form.getId())) {
            return updateItem(form);
        } else {
            return insertItem(form);
        }
    }

    private Item insertItem(ItemSaveForm form) {
        Item item = ItemSaveForm.createItem(form);
        Space space = getSpace(form.getSpaceName());
        item.changeSpace(space);
        itemRepository.save(item);
        return item;
    }

    private Item updateItem(ItemSaveForm form) {
        Space space = getSpace(form.getSpaceName());
        Item item = itemRepository.getReferenceById(form.getId());
        item.changeName(form.getName())
                .changeDescription(form.getDescription())
                .changeSpace(space)
                .changePurchaseDate(form.getPurchaseDate())
                .changeDisposeFlg(form.getDisposeFlg())
                .changeDisappearFlg(form.getDisappearFlg())
                .changeExpirationDate(form.getExpirationDate());
        return item;
    }

    private Space getSpace(String spaceName) {
        if (StringUtils.hasText(spaceName)) {
            Space space = spaceRepository.findByName(spaceName);
            if (space == null) {
                throw new ApiException(NOT_EXIST_DATA_ERROR, SPACE.code());
            }
            return space;
        }
        return null;
    }

    public void delItem(Long id) {
        existItem(id);
        itemRepository.deleteById(id);
    }

    public List<ItemDto> findAllItemByName(ItemSearchCond searchCond) {
        List<ItemDto> items = itemRepository.searchItem(searchCond);
        if (items.isEmpty()) {
            throw new ApiException(NOT_EXIST_ERROR);
        }
        return items;
    }

}
