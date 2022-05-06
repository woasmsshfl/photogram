package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder // Builder 패턴으로 데이터를 담을 수 있게 해주는 어노테이션
@AllArgsConstructor // 모든 생성자를 자동으로 만들어주는 어노테이션
@NoArgsConstructor // 빈 생성자를 자동으로 만들어주는 어노테이션
@Data // 자동으로 Getter, Setter, toString을 만들어주는 어노테이션
@Entity // DB에 테이블을 생성해주는 어노테이션
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id // Primary Key를 지정해주는 어노테이션
    private Integer id; // 데이터가 들어갈 때 마다 번호를 매겨줄것임.

    @Column(unique = true, length = 20, nullable = false)
    private String username; // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String name; // 별명

    private String website; // 개인 웹사이트 주소

    private String bio; // 자기소개

    @Column(nullable = false)
    private String email; // 이메일

    private String phone; // 전화번호

    private String gender; // 성별

    private String prifileImageUrl; // 프로필 사진

    private String role; // 권한 

    private LocalDateTime createDate; // 데이터가 입력된 시간.

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}