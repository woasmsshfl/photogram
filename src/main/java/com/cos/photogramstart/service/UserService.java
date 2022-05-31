package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public User 회원프로필사진변경(Integer principalId, MultipartFile profileImageFile) {
        
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + profileImageFile.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        try {
            Files.write(imageFilePath, profileImageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        User userEntity = userRepository.findById(principalId).orElseThrow(()->{
            throw new CustomApiException("존재하지 않는 유저입니다.");
        });

        userEntity.setProfileImageUrl(imageFileName);

        return userEntity;
    }

    @Transactional(readOnly = true)
    public UserProfileDto 회원프로필(Integer pageUserId, Integer principalId) { // 해당 페이지 주인의 ID를 받아준다.
        // 페이지 주인여부와 로그인한 유저의 정보를 담을 DTO 받아주기
        UserProfileDto dto =new UserProfileDto(); // 아직 영속화 되지 않은 DTO

        // SELECT * FROM image WHERE userId = :userId;
        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomException("존재하지 않는 유저의 페이지입니다.");
        });

        // DTO에 페이지 주인여부와 페이지에 접속한 유저데이터 담기 + 이미지 개수 담기
        dto.setUser(userEntity); // 유저 오브젝트
        dto.setPageOwnerState(pageUserId == principalId); // true AND false
        dto.setImageCount(userEntity.getImages().size()); // User가 가지고 있는 이미지 개수

        // DTO에 구독정보 담기
        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState == 1);
        dto.setSubscribeCount(subscribeCount);

        userEntity.getImages().forEach((image) -> {
            image.setLikeCount(image.getLikes().size());
        });

        return dto;
    }

    @Transactional
    public User 회원수정(Integer id, User user) {
        // 1. 영속화 하기
        User userEntity = 
        userRepository.findById(id).orElseThrow(() -> {
            return new CustomValidationApiException("찾을 수 없는 ID입니다.");
                });
        

        // 2. 영속화된 오브젝트 수정하기
        userEntity.setName(user.getName());
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(user.getPassword());
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        if (userEntity.getPassword() == null) {
            return userEntity;
        } else {
            userEntity.setPassword(encPassword);
            return userEntity;
        }

    } // 3. 리턴 완료되면 더티체킹이 일어나면서 업데이트가 완료됨.
    
}
