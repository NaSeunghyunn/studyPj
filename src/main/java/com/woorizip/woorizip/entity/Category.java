package com.woorizip.woorizip.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
    @OneToMany(mappedBy = "category")
    private List<ItemCategory> itemCategory = new ArrayList<>();

    @Builder
    public Category(Long id, String name, Category parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    public void changeParent(Category parent) {
        if (parent == null || this.parent == null) {
            this.parent = null;
        } else if (parent.getId() != this.id) {
            this.parent = parent;
            parent.child.add(this);
        }
    }

    public void changeName(String name) {
        this.name = name;
    }
}
