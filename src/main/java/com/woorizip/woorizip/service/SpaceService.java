package com.woorizip.woorizip.service;

import com.woorizip.woorizip.Repository.ItemRepository;
import com.woorizip.woorizip.Repository.SpaceRepository;
import com.woorizip.woorizip.controller.form.SpaceDeleteForm;
import com.woorizip.woorizip.controller.form.SpaceSaveForm;
import com.woorizip.woorizip.controller.form.SpaceUpdateForm;
import com.woorizip.woorizip.dto.SpaceDto;
import com.woorizip.woorizip.entity.Space;
import com.woorizip.woorizip.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.woorizip.woorizip.util.MessageCode.*;
import static com.woorizip.woorizip.util.NameCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;
    private final ItemRepository itemRepository;

    public List<SpaceDto> findSpaceName(String name) {
        List<Space> spaces = spaceRepository.findByNameContains(name);
        return SpaceDto.createDto(spaces);
    }

    public List<SpaceDto> findSpaceList(String name) {
        List<Space> spaces = getList(name);
        return SpaceDto.createDto(spaces);
    }

    private List<Space> getList(String name) {
        if(!StringUtils.hasText(name)) return spaceRepository.findByParentIsNull();
        return spaceRepository.findByNameContains(name);
    }

    public List<SpaceDto> findSpaceChildren(Long id) {
        existSpace(id);
        List<Space> children = getChildren(id);
        return SpaceDto.createDto(children);
    }

    private List<Space> getChildren(Long id) {
        if(id == null || id < 1L) return spaceRepository.findByParentIsNull();
        return spaceRepository.findByParentId(id);
    }

    private void existSpace(Long id) {
        boolean existSpace = spaceRepository.existsById(id);
        if (!existSpace) {
            throw new ApiException(NOT_EXIST_ERROR);
        }
    }

    public SpaceDto saveSpace(SpaceSaveForm form) {
        existSpaceName(form.getSpaceName());
        Space newSpace = insertSpace(form);
        return SpaceDto.createDto(newSpace);
    }

    private Space insertSpace(SpaceSaveForm form) {
        Space parent = getParent(form.getParentName());
        Space newSpace = Space.builder().name(form.getSpaceName()).parent(parent).build();
        spaceRepository.save(newSpace);
        return newSpace;
    }

    private Space getParent(String name) {
        if(!StringUtils.hasText(name)) return null;

        boolean existSpace = spaceRepository.existsByName(name);
        if (existSpace) {
            return spaceRepository.findByName(name);
        }

        throw new ApiException(NOT_EXIST_DATA_ERROR, PARENT_SPACE.code());
    }

    private void existSpaceName(String name) {
        boolean existName = spaceRepository.existsByName(name);
        if (existName) {
            throw new ApiException(EXISTS_REGISTER_ERROR, SPACE_NAME.code());
        }
    }

    public Long updateSpace(SpaceUpdateForm form) {
        existSpace(form.getId());
        checkDuplicateName(form);
        Space space = spaceRepository.getReferenceById(form.getId());
        space.changeName(form.getName());
        return space.getId();
    }

    private void checkDuplicateName(SpaceUpdateForm form) {
        boolean isDuplicateName = spaceRepository.existsByNameAndIdNot(form.getName(), form.getId());
        if(isDuplicateName){
            throw new ApiException(EXISTS_REGISTER_ERROR, SPACE_NAME.code());
        }
    }

    public void deleteSpace(SpaceDeleteForm form) {
        existSpace(form.getId());
        Space space = spaceRepository.getReferenceById(form.getId());
        processingSpace(space);
        List<Space> children = getChildren(form.getId());
        children.forEach(child -> child.changeParent(space.getParent()));
        spaceRepository.delete(space);
    }

    private void processingSpace(Space space) {
        boolean processingSpace = itemRepository.existsBySpace(space);
        if (processingSpace) {
            throw new ApiException(PROCESSING_DELETE_ERROR, SPACE.code());
        }
    }

}
