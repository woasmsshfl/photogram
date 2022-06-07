package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

    // 6. 이미지가 저장되는 경로 호출하기
    @Value("${file.path}")
    private String uploadFolder;
    
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<Image> 모든이미지(Integer principalId, Pageable pageable) {
        List<Image> images = imageRepository.mStoryAll(pageable);
        images.forEach((image) -> {
            image.setLikeCount(image.getLikes().size());
            image.getLikes().forEach((like) -> {
                if (like.getUser().getId() == principalId) {
                    image.setLikeState(true);
                }
            });
        });

        return images;
    }

    @Transactional(readOnly = true)
    public List<Image> 인기사진() {
        return imageRepository.mPopular();
    }

    @Transactional(readOnly = true)
    public List<Image> 이미지스토리(Integer principalId, Pageable pageable) {
        List<Image> images = imageRepository.mStory(principalId, pageable);

        // // images의 좋아요 상태 담기(이중for문)
        images.forEach((image) -> {
            // 좋아요 카운트 담기
            image.setLikeCount(image.getLikes().size());

            image.getLikes().forEach((like) -> {
                if (like.getUser().getId() == principalId) {
                    image.setLikeState(true);
                }
            });
        });

        return images;
    }

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto,
            PrincipalDetails principalDetails) {
        // 2. UUID 객체를 생성한다.
        UUID uuid = UUID.randomUUID();
        // 1. 업로드 되는 원본 파일명을 imageFileName라고 지정한다.
        // 3. UUID를 더한 값으로 지정한다.
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        // 4. UUID가 적용된 파일명 확인하기
        // System.out.println(imageFileName);
        // 5. image 저장 경로 지정하기
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);
        
        // 7. 파일을 업로드하기
        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 8. image 파일경로를 DB에 INSERT하기
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        imageRepository.save(image);
        // System.out.println("imageEntity : "+imageEntity);
    }
}
