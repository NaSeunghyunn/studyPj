package com.woorizip.woorizip.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CategorySaveForm {
    private Long id;
    @NotBlank
    private String categoryName;
    private String parentName;
}
