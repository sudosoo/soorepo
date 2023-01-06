package com.example.board.service.comment;

import com.example.board.dto.CommentRequestDto;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    @Transactional
    void commentCreate(Long id, String authUserName, CommentRequestDto commentRequestDto);

    @Transactional
    void commentDelete(Long id, Long cid, String writer);

    @Transactional
    void commentUpdate(Long id, Long cid, String commentRequestDto, String writer);

    @Transactional
    void createcommentRefly(Long id, Long cid, String commentRequestDto, String writer);
}
