package com.woorizip.woorizip.controller.form;

import com.woorizip.woorizip.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ItemSaveForm {
    private Long id;
    @NotBlank
    private String name;
    private String spaceName;
    private String purchaseDate;
    private String expirationDate;
    @Size(min = 1,max = 1)
    private String disposeFlg;
    @Size(min = 1,max = 1)
    private String disappearFlg;
    private String description;
    private List<Long> categoryIds;

    public static Item createItem(ItemSaveForm formDto) {
        return Item.builder()
                .name(formDto.getName())
                .purchaseDate(formDto.getPurchaseDate())
                .disposeFlg(formDto.getDisposeFlg())
                .disappearFlg(formDto.getDisappearFlg())
                .expirationDate(formDto.getExpirationDate())
                .description(formDto.getDescription())
                .build();
    }
}
