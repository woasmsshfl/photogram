package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
    
    private boolean PageOwnerState; // 페이지 주인 여부 [1 : 주인o] [-1 : 주인x]

    private Integer imageCount; // 업로드된 페이지 개수

    private boolean subscribeState; // 구독상태. 했으면 true 안했으면 false

    private Integer subscribeCount; // 구독자 수 카운팅

    private User user; // 접속한 유저정보를 받을 유저 오브젝트
}
