package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
    
    // 로그인한 유저가 모달에서 확인할 유저의 ID
    private Integer id;
    
    // 로그인한 유저가 모달에서 확인할 유저의 이름
    private String username;

    // 로그인한 유저가 모달에서 확인할 유저의 프로필이미지
    private String profileImageUrl;

    // 로그인한 유저가 모달에서 확인한 유저를 구독했는지에 대한 여부
    private Integer subscribeState;

    // 로그인한 유저가 모달에서 확인한 유저가 본인 스스로인지에 대한 여부
    private Integer equalUserState;
}
