package com.example.board.service;

import com.example.board.checkUtil.CheckUtil;
import com.example.board.dto.CommentRequestDto;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.entity.User;
import com.example.board.entity.UserRoleEnum;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CheckUtil checkUtil;
    private final BoardRepository boardRepository;

    @Transactional
    public void commentCreate(Long id, CommentRequestDto commentRequestDto, String authUserName) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        Optional<User> userInfo = userRepository.findByUsername(authUserName);
        String username = userInfo.get().getUsername();
        Comment comment = new Comment(board, username, commentRequestDto);
        board.addComment(comment);
        commentRepository.save(comment); // 새로 생성된 커멘트 insert
    }

    @Transactional
    public void commentDelete(Long id, Long cid, String authUserName, CommentRequestDto commentRequestDto) {
        User user = userRepository.findByUsername(authUserName).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        Comment comment = commentRepository.findById(cid).orElseThrow(() -> new IllegalArgumentException("댓글이 존재 하지 않습니다."));
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (user.getRole() == UserRoleEnum.ADMIN) {
            commentRepository.delete(comment);
        } else if (user.getRole() == UserRoleEnum.USER) {
            if (checkUtil.commentPasswordCheck(authUserName, comment, commentRequestDto)) {
                commentRepository.delete(comment);
            } else {
                throw new IllegalArgumentException("댓글의 비밀번호가 일치하지 않습니다.");
            }
        }
    }

    @Transactional
    public void commentUpdate(Long id, Long cid, String authUserName, CommentRequestDto commentRequestDto) {
        User user = userRepository.findByUsername(authUserName).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        Comment comment = commentRepository.findById(cid).orElseThrow(() -> new IllegalArgumentException("댓글이 존재 하지 않습니다."));
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (user.getRole() == UserRoleEnum.ADMIN) {
            comment.changeContents(commentRequestDto);
            commentRepository.save(comment);
        } else if (user.getRole() == UserRoleEnum.USER) {
            if (checkUtil.commentPasswordCheck(authUserName, comment, commentRequestDto)) {
                comment.changeContents(commentRequestDto);
                commentRepository.save(comment);
            } else {
                throw new IllegalArgumentException("댓글의 비밀번호가 일치하지 않습니다.");
            }
        }
    }


}
