package com.cos.photogramstart.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Integer> {
    
    @Modifying
    @Query(value = "INSERT INTO likes(imageId, userId, createDate) VALUES(:imageId, :principalId, now())", nativeQuery = true)
    Integer mLikes(Integer imageId, Integer principalId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE imageId = :imageId AND userId = :principalId", nativeQuery = true)
    Integer mUnLikes(Integer imageId, Integer principalId);
}
