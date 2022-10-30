package com.woorizip.woorizip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/item")
public class ItemController {

    @GetMapping("/{id}")
    public String detailInit(@PathVariable("id") Long id, @RequestParam(defaultValue = "/item") String referer, Model model) {
        model.addAttribute("referer", referer);
        return "itemDetail";
    }

    @GetMapping("")
    public String itemListInit() {
        return "item";
    }

    @GetMapping("/expire")
    public String expireItems() {
        return "expireItem";
    }
}
