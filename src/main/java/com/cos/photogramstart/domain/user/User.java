package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @Column(unique = true, nullable = false)
    private String username; // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호

    private String name; // 별명

    private String website; // 개인 웹사이트 주소

    private String bio; // 자기소개

    private String email; // 이메일

    private String phone; // 전화번호

    private String gender; // 성별

    private String profileImageUrl; // 프로필 사진

    private String role; // 권한 

    // Image오브젝트와의 양방향매핑
    // 연관관계의 주인이 User가 아니다.
    // 따라서, User 테이블을 생성할 때, images 테이블을 만들지 않겠다.
    // User오브젝트를 Select할 때, 해당 userId로 Upload된 모든 image들을 같이 가져와야한다.
    // LAZY : User 오브젝트를 SELECT할 때, 해당 userId로 등록된 모든 image들을 가져오지 않겠다.
    // EAGER : User 오브젝트를 SELECT할 때, 해당 userId로 등록된 모든 image들을 가져오겠다.
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"}) // 이 오브젝트 내부에 있는 user를 JSON으로 파싱하지 않겠다.
    private List<Image> images; // 프로필페이지를 응답할 때, 같이 담아올 image의 정보

    private LocalDateTime createDate; // 데이터가 입력된 시간.

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User [bio=" + bio + ", createDate=" + createDate + ", email=" + email + ", gender=" + gender + ", id="
                + id + ", name=" + name + ", password=" + password + ", phone=" + phone + ", profileImageUrl="
                + profileImageUrl + ", role=" + role + ", username=" + username + ", website=" + website + "]";
    }

    
}