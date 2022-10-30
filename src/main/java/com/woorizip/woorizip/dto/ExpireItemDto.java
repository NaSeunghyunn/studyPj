package com.woorizip.woorizip.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.woorizip.woorizip.entity.Item;
import com.woorizip.woorizip.util.DateUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class ExpireItemDto {
    private Long id;
    private String name;
    private String spaceName;
    private String itemDescription;
    private String expirationDate;

    @Builder
    @QueryProjection
    public ExpireItemDto(Long id, String name, String spaceName, String desc, LocalDate expirationDate) {
        this.id = id;
        this.name = name;
        this.spaceName = spaceName;
        this.itemDescription = desc;
        this.expirationDate = DateUtil.formatDate(expirationDate);
    }
}
