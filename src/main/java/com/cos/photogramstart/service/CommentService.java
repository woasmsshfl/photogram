package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment 댓글쓰기() {
        return null;
    }

    @Transactional
    public void 댓글삭제() {

    }
    
}
