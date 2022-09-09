package com.woorizip.woorizip.Repository;

import com.woorizip.woorizip.Repository.nativeQuery.SpaceNativeQuery;
import com.woorizip.woorizip.entity.Space;

import java.util.List;

public interface SpaceRepository extends SpaceNativeQuery {
    List<Space> findByIdIn(List<Long> ids);
    List<Space> findByNameContains(String name);
    List<Space> findByParentId(Long parentId);
    List<Space> findByParentIsNull();
    Space findByName(String name);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}
