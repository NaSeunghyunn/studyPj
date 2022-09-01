package com.woorizip.woorizip.Repository;

import com.woorizip.woorizip.entity.Category;
import com.woorizip.woorizip.entity.Item;
import com.woorizip.woorizip.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    @Query(value = "SELECT ic FROM ItemCategory ic join fetch ic.category WHERE ic.item = :item")
    List<ItemCategory> findFetchJoinByItem(@Param("item") Item item);

    @Query(value = "SELECT ic.category FROM ItemCategory ic WHERE ic.item = :item")
    List<Category> findCategoryByItem(@Param("item") Item item);

    boolean existsByCategory(Category category);

    void deleteByItem(Item item);
}
