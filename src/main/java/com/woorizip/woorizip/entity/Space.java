package com.woorizip.woorizip.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Space {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Space parent;
    @OneToMany(mappedBy = "parent")
    private List<Space> child = new ArrayList<>();
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;

    @Builder
    public Space(String name, Space parent) {
        this.name = name;
        this.parent = parent;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changeParent(Space parent){
        this.parent = parent;
    }
}
