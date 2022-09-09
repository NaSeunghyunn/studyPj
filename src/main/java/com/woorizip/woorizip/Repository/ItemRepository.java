package com.woorizip.woorizip.Repository;

import com.woorizip.woorizip.Repository.querydsl.ItemRepositoryCustom;
import com.woorizip.woorizip.entity.Item;
import com.woorizip.woorizip.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    boolean existsBySpace(Space space);
}
