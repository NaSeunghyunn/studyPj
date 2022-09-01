package com.woorizip.woorizip.controller.api;

import com.woorizip.woorizip.dto.SpaceDto;
import com.woorizip.woorizip.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
