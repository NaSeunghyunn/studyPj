package com.woorizip.woorizip.service;

import com.woorizip.woorizip.Repository.CategoryRepository;
import com.woorizip.woorizip.Repository.ItemCategoryRepository;
import com.woorizip.woorizip.dto.CategoryDetailDto;
import com.woorizip.woorizip.dto.CategoryDto;
import com.woorizip.woorizip.entity.Category;
import com.woorizip.woorizip.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.woorizip.woorizip.util.NameCode.*;
import static com.woorizip.woorizip.util.MessageCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    public Long saveCategory(Long id, String name, String parentName) {
        if (id != null && id > 0L) {
            return updateCategory(id, name, parentName);
        } else {
            return insertCategory(id, name, parentName);
        }
    }

    private void existsName(String name) {
        boolean existsByName = categoryRepository.existsByName(name);
        if (existsByName) {
            throw new ApiException(EXISTS_REGISTER_ERROR, CATEGORY_NAME.code());
        }
    }

    public CategoryDetailDto findCategory(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        CategoryDetailDto dto = new CategoryDetailDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        List<Category> parentNames = getParents(category);
        dto.setParents(CategoryDto.createDto(parentNames));
        return dto;
    }

    public List<CategoryDto> findCategories() {
        List<Category> categories = categoryRepository.findAll();
        return CategoryDto.createDto(categories);
    }

    public List<CategoryDto> findCategoryName(String name) {
        List<Category> categories = categoryRepository.findByNameContains(name);
        if(categories.isEmpty()){
            throw new ApiException(NOT_EXIST_ERROR);
        }
        return CategoryDto.createDto(categories);
    }

    public List<CategoryDto> findCategoryForLevel(Long id) {
        List<Category> categories = new ArrayList<>();
        if (id == null || id < 1L) {
            categories = categoryRepository.findByParentIsNull();
        } else {
            categories = categoryRepository.findByParentId(id);
        }
        Collections.reverse(categories);
        return CategoryDto.createDto(categories);
    }

    public Long delCategory(Long id) {
        Category category = categoryRepository.getReferenceById(id);
        checkProcessingCategory(category);
        Category parent = category.getParent();
        categoryRepository.updateParentOfChild(category, parent);
        categoryRepository.delete(category);
        return category.getId();
    }

    private void checkProcessingCategory(Category category) {
        boolean existItemCategory = itemCategoryRepository.existsByCategory(category);
        if(existItemCategory){
            throw new ApiException(PROCESSING_DELETE_ERROR, CATEGORY.code());
        }
    }

    private List<Category> getParents(Category category) {
        List<Long> parentIds = categoryRepository.findParentIds(category.getId());
        List<Category> parents = categoryRepository.findByIdIn(parentIds);
        return parents;
    }

    private Long insertCategory(Long id, String name, String parentName) {
        existsName(name);
        Category parent = getCategoryByName(parentName);
        Category category = Category.builder()
                .name(name)
                .parent(parent)
                .build();
        categoryRepository.save(category);
        return category.getId();
    }

    private Long updateCategory(Long id, String name, String parentName) {
        Category category = categoryRepository.getReferenceById(id);
        if(!category.getName().equals(name)){
            existsName(name);
        }
        category.changeName(name);
        if (category.getParent() != null) {
            Category parent = getCategoryByName(parentName);
            List<Long> parentIds = categoryRepository.findParentIds(parent.getId());
            if (!parentIds.contains(category.getId())) {
                category.changeParent(parent);
            } else {
                throw new ApiException(LOWER_LAYER_MOD_ERROR, CATEGORY.code());
            }
        }
        return category.getId();
    }

    private Category getCategoryByName(String name) throws ApiException {
        Category category = null;
        if (StringUtils.hasText(name)) {
            category = categoryRepository.findByName(name);
            if (category == null) {
                throw new ApiException(NOT_EXIST_DATA_ERROR, PARENT_CATEGORY.code());
            }
        }
        return category;
    }
}
