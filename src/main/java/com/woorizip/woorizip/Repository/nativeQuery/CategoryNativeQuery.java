package com.woorizip.woorizip.Repository.nativeQuery;

import com.woorizip.woorizip.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryNativeQuery extends JpaRepository<Category, Long> {
    @Query(value = "WITH RECURSIVE cte AS ( SELECT id, parent_id FROM category WHERE id = ? UNION ALL SELECT b.id,b.parent_id FROM cte AS a INNER JOIN category AS b ON b.id = a.parent_id) SELECT id FROM cte", nativeQuery = true)
    List<Long> findParentIds(Long id);
}
