package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
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
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "likes_uk", // Unique 제약조건 이름
                columnNames = { // Unique 제약조건을 적용할 컬럼명
                        "imageId",
                        "userId"
                })
})
public class Likes {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id // Primary Key를 지정해주는 어노테이션
    private Integer id; // 데이터가 들어갈 때 마다 번호를 매겨줄것임.

    @ManyToOne
    @JoinColumn(name = "imageId")
    private Image image; // 좋아요가 된 것이 어떤 이미지인지.

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"images"})
    private User user; // 좋아요를 누가 한 것인지.

    private LocalDateTime createDate; // 데이터가 입력된 시간.

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
