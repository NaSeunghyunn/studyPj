package com.woorizip.woorizip.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ItemDeleteForm {
    @NotNull
    private Long id;
}
