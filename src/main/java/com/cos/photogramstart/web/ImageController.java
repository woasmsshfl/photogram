package com.cos.photogramstart.web;

import java.util.List;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping({"/", "/image/all"})
    public String story() {
        return "image/story";
    }

    @GetMapping("/image/popular")
    public String popular(Model model) {
        List<Image> images = imageService.인기사진();
        model.addAttribute("images", images);
        return "image/popular";
    }

    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        imageService.사진업로드(imageUploadDto, principalDetails);
        if (imageUploadDto.getFile().isEmpty()) { // imageUploadDto에서 File이 없으면
            throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
        } else {
            return "redirect:/user/" + principalDetails.getUser().getId();
        }
    }
}
