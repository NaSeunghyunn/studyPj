package com.woorizip.woorizip.Repository.nativeQuery;

import com.woorizip.woorizip.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpaceNativeQuery extends JpaRepository<Space, Long> {
    @Query(value = "WITH RECURSIVE cte AS ( SELECT id, parent_id FROM space WHERE id = ? UNION ALL SELECT space.id, space.parent_id FROM cte AS c INNER JOIN space AS space ON space.id = c.parent_id) SELECT id FROM cte " , nativeQuery = true)
    public List<Long> findParentIds(Long id);
}
