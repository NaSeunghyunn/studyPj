package com.woorizip.woorizip.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "space_id")
    private Space space;
    @Column(length = 10)
    private String purchaseDate;
    @Column(length = 10)
    private String expirationDate;
    private String disposeFlg;
    private String disappearFlg;
    @OneToMany(mappedBy = "item")
    private List<ItemCategory> itemCategory = new ArrayList<>();

    @Builder
    public Item(Long id, String name, String description, Space space, String purchaseDate, String expirationDate, String disposeFlg, String disappearFlg) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.space = space;
        this.purchaseDate = purchaseDate;
        this.expirationDate = expirationDate;
        this.disposeFlg = disposeFlg;
        this.disappearFlg = disappearFlg;
    }

    public Item changeName(String name) {
        this.name = name;
        return this;
    }

    public Item changeDescription(String description) {
        this.description = description;
        return this;
    }

    public Item changeSpace(Space space) {
        this.space = space;
        return this;
    }

    public Item changePurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public Item changeDisposeFlg(String disposeFlg) {
        this.disposeFlg = disposeFlg;
        return this;
    }

    public Item changeDisappearFlg(String disappearFlg) {
        this.disappearFlg = disappearFlg;
        return this;
    }

    public Item changeExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }
}
