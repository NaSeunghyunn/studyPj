package com.woorizip.woorizip.Repository;

import com.woorizip.woorizip.Repository.nativeQuery.CategoryNativeQuery;
import com.woorizip.woorizip.entity.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CategoryNativeQuery {
    List<Category> findByNameContains(String name);

    List<Category> findByIdIn(List<Long> ids);

    List<Category> findByParentId(Long parentId);

    List<Category> findByParentIsNull();

    Category findByName(String name);

    @Modifying
    @Query("update Category child set child.parent = :parentOfTarget where child.parent = :target")
    int updateParentOfChild(@Param("target") Category target, @Param("parentOfTarget") Category parentOfTarget);

    boolean existsByName(String name);
}
