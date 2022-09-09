package com.woorizip.woorizip.controller.api;

import com.woorizip.woorizip.controller.form.SpaceDeleteForm;
import com.woorizip.woorizip.controller.form.SpaceSaveForm;
import com.woorizip.woorizip.controller.form.SpaceUpdateForm;
import com.woorizip.woorizip.dto.ResponseDto;
import com.woorizip.woorizip.dto.SpaceDto;
import com.woorizip.woorizip.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/space")
@RequiredArgsConstructor
public class SpaceApiController {

    private final SpaceService spaceService;

    @GetMapping("/search")
    public List<SpaceDto> searchSpace(@RequestParam String name){
        return spaceService.findSpaceName(name);
    }

    @GetMapping("/searchList")
    public List<SpaceDto> searchSpaceList(@RequestParam String name){
        return spaceService.findSpaceList(name);
    }

    @GetMapping("/search/children/{id}")
    public List<SpaceDto> searchSpaceChildren(@PathVariable Long id){
        return spaceService.findSpaceChildren(id);
    }

    @PostMapping("/save")
    public SpaceDto saveSpace(@RequestBody @Validated SpaceSaveForm form){
        return spaceService.saveSpace(form);
    }

    @PostMapping("/update")
    public Long updateSpace(@RequestBody SpaceUpdateForm form){
        return spaceService.updateSpace(form);
    }

    @DeleteMapping("")
    public ResponseDto deleteSpace(@RequestBody SpaceDeleteForm form){
        spaceService.deleteSpace(form);
        return ResponseDto.builder().message("success").build();
    }
}
