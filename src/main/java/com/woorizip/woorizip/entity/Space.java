package com.woorizip.woorizip.entity;

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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    private String description;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Space parent;
    @OneToMany(mappedBy = "parent")
    private List<Space> child = new ArrayList<>();
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "house_id")
    private House house;
    @OneToMany(mappedBy = "space")
    private List<Item> items = new ArrayList<>();
}
