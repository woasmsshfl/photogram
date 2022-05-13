package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

        // 2-1. password 암호화 하기
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userEntity.setPassword(encPassword);
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    } // 3. 리턴 완료되면 더티체킹이 일어나면서 업데이트가 완료됨.
    
}
