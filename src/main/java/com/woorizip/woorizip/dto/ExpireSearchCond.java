package com.woorizip.woorizip.dto;

import com.woorizip.woorizip.controller.form.ExpireItemForm;
import com.woorizip.woorizip.util.DateUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ExpireSearchCond {
    private LocalDate expireDate;

    public static ExpireSearchCond of(ExpireItemForm form){
        return ExpireSearchCond.builder()
                .expireDate(DateUtil.getPlusDateFromNow(form.getExpirationDays()))
                .build();
    }
}
