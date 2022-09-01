package com.woorizip.woorizip.service;

import com.woorizip.woorizip.Repository.SpaceRepository;
import com.woorizip.woorizip.dto.SpaceDto;
import com.woorizip.woorizip.entity.Space;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;

    public List<SpaceDto> findSpaceName(String name) {
        List<Space> spaces = spaceRepository.findByNameContains(name);
        return SpaceDto.createDto(spaces);
    }

}
