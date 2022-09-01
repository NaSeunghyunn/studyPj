package com.woorizip.woorizip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @GetMapping("")
    public String init(Model model){
        return "category";
    }

    @GetMapping("/{id}")
    public String detailInit(@PathVariable("id") Long id, Model model){
        model.addAttribute("id",id);
        return "categoryDetail";
    }
}
