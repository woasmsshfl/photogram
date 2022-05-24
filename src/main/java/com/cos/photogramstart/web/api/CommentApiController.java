package com.cos.photogramstart.web.api;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.service.CommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave() {
        Comment commentEntity = commentService.댓글쓰기();
        return null;
    }
    
    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable Integer id) {
        commentService.댓글삭제();
        return null;
    }
    
}
