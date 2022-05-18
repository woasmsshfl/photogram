package com.cos.photogramstart.web.api;

import java.util.List;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.CMRespDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {
    
    private final ImageService imageService;

    @GetMapping("/api/image")
    public ResponseEntity<?> imageStory(
        @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
        @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<Image> images = imageService.이미지스토리(principalDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(
            new CMRespDto<>(1, "성공", images), HttpStatus.OK);
    }
}
