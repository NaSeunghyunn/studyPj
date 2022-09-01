package com.woorizip.woorizip.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class House {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
}
