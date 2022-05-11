package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    
    // 해당 image를 설명하는 영역
    private String caption;

    // image가 서버로 전송되어 저장되는 경로
    private String postImageUrl;

    // image를 누가 올렸는지 알기 위해 받은 유저오브젝트
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    // 이미지 좋아요

    // 이미지 좋아요 카운팅

    // 댓글 정보

    private LocalDateTime createDate; // 데이터가 입력된 시간.

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
