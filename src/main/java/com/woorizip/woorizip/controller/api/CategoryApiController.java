package com.woorizip.woorizip.controller.api;

import com.woorizip.woorizip.controller.form.CategoryDeleteForm;
import com.woorizip.woorizip.controller.form.CategorySaveForm;
import com.woorizip.woorizip.dto.CategoryDetailDto;
import com.woorizip.woorizip.dto.CategoryDto;
import com.woorizip.woorizip.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public List<CategoryDto> getCategoryForLevel(@PathVariable("id") Long id) {
        return categoryService.findCategoryForLevel(id);
    }

    @GetMapping("/detail")
    public List<CategoryDto> getCategories() {
        return categoryService.findCategories();
    }

    @GetMapping("/detail/{id}")
    public CategoryDetailDto getCategory(@PathVariable("id") Long id) {
        return categoryService.findCategory(id);
    }

    @PostMapping("/detail")
    public Long genCategory(@Validated @RequestBody CategorySaveForm form) {
        return categoryService.saveCategory(form.getId(), form.getCategoryName(), form.getParentName());
    }

    @DeleteMapping("/detail")
    public Long delCategory(@RequestBody CategoryDeleteForm form) {
        return categoryService.delCategory(form.getId());
    }

    @GetMapping("/search")
    public List<CategoryDto> searchCategory(@RequestParam String name) {
        return categoryService.findCategoryName(name);
    }

}
