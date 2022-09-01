package com.woorizip.woorizip.Repository;

import com.woorizip.woorizip.Repository.nativeQuery.SpaceNativeQuery;
import com.woorizip.woorizip.entity.Space;

import java.util.List;

public interface SpaceRepository extends SpaceNativeQuery {
    List<Space> findByIdIn(List<Long> ids);
    List<Space> findByNameContains(String name);
    Space findByName(String name);
}
