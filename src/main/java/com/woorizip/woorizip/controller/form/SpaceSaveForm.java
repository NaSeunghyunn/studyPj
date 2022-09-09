package com.woorizip.woorizip.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SpaceSaveForm {
    @NotBlank
    private String spaceName;
    private String parentName;
}
