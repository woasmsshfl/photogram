package com.cos.photogramstart.domain.subscribe;

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

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder // Builder 패턴으로 데이터를 담을 수 있게 해주는 어노테이션
@AllArgsConstructor // 모든 생성자를 자동으로 만들어주는 어노테이션
@NoArgsConstructor // 빈 생성자를 자동으로 만들어주는 어노테이션
@Data // 자동으로 Getter, Setter, toString을 만들어주는 어노테이션
@Entity // DB에 테이블을 생성해주는 어노테이션
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name="subscribe_uk", // Unique 제약조건 이름
            columnNames = { // Unique 제약조건을 적용할 컬럼명
                "fromUserId", 
                "toUserId"
            }
        )
    }
)
public class Subscribe {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id // Primary Key를 지정해주는 어노테이션
    private Integer id; // 데이터가 들어갈 때 마다 번호를 매겨줄것임.
    
    @JoinColumn(name = "fromUserId")
    @ManyToOne
    private User fromUser; // 구독자

    @JoinColumn(name = "toUserId")
    @ManyToOne
    private User toUser; // 피구독자

    private LocalDateTime createDate; // 데이터가 입력된 시간.

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
