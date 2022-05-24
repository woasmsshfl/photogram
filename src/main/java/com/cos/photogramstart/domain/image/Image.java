package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    // 이미지 좋아요
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    // 이미지 좋아요 여부 상태
    @Transient // DB에 해당 컬럼을 생성하지 않게 만드는 어노테이션
    private boolean likeState;

    // 이미지 좋아요 카운팅
    @Transient
    private Integer likeCount;

    // 댓글 정보
    @OrderBy("id DESC")
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Comment> comments;

    private LocalDateTime createDate; // 데이터가 입력된 시간.

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
    // 오브젝트를 sysout할 때, 무한참조가 발생하는 User오브젝트를 삭제한 toString
    // @Override
    // public String toString() {
    //     return "Image [caption=" + caption + ", createDate=" + createDate + ", id=" + id + ", postImageUrl="
    //             + postImageUrl + "]";
    // }
}
