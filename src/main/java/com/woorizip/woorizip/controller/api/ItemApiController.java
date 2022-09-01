package com.woorizip.woorizip.controller.api;

import com.woorizip.woorizip.controller.form.ItemDeleteForm;
import com.woorizip.woorizip.controller.form.ItemForm;
import com.woorizip.woorizip.controller.form.ItemSaveForm;
import com.woorizip.woorizip.dto.ItemDetailDto;
import com.woorizip.woorizip.dto.ItemDto;
import com.woorizip.woorizip.dto.ItemSearchCond;
import com.woorizip.woorizip.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    @PostMapping("")
    public List<ItemDto> searchItems(@RequestBody ItemForm form) {
        ItemSearchCond searchCond = ItemSearchCond.of(form);
        return itemService.findAllItemByName(searchCond);
    }

    @GetMapping("/detail/{id}")
    public ItemDetailDto getItem(@PathVariable("id") Long id) {
        return itemService.getItem(id);
    }

    @PostMapping("/detail")
    public Long saveItem(@Validated @RequestBody ItemSaveForm form) {
        return itemService.saveItem(form);
    }

    @DeleteMapping("/detail")
    public Long delItem(@Validated @RequestBody ItemDeleteForm form) {
        itemService.delItem(form.getId());
        return form.getId();
    }
}
