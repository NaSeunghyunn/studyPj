package com.woorizip.woorizip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/item")
public class ItemController {

    @GetMapping("/{id}")
    public String detailInit(@PathVariable("id") Long id, Model model){
        model.addAttribute("id", id);
        return "itemDetail";
    }

    @GetMapping("")
    public String itemListInit(){
        return "item";
    }
}
