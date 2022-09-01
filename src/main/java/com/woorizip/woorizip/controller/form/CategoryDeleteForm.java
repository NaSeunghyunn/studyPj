package com.woorizip.woorizip.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CategoryDeleteForm {
    @NotNull
    private Long id;
}
