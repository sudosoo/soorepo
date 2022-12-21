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
import io.jsonwebtoken.Claims;
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
    public String commentCreate(Long id, CommentRequestDto commentRequestDto, Claims claims) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        Optional<User> userInfo = userRepository.findByUsername(claims.getSubject());
        String username = userInfo.get().getUsername();
        Comment comment = new Comment(board, username, commentRequestDto);
        board.addComment(comment);
        commentRepository.save(comment); // 새로 생성된 커멘트 insert
        boardRepository.save(board);  // 댓글이 추가됐으니가 update
        return comment.getCommentContents();
    }

    @Transactional
    public String commentDelete(Long id, Long cid, Claims claims, CommentRequestDto commentRequestDto) {
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        Comment comment = commentRepository.findById(cid).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (user.getRole() == UserRoleEnum.ADMIN) {
            commentRepository.delete(comment);
            boardRepository.save(board);
        } else if (user.getRole() == UserRoleEnum.USER) {
            if (checkUtil.commentPasswordCheck(claims, comment, commentRequestDto)) {
                commentRepository.delete(comment);
                boardRepository.save(board);
            } else {
                throw new IllegalArgumentException("댓글의 비밀번호가 일치하지 않습니다.");
            }
        }
        return comment.toString("댓글삭제가 완료 되었습니다.");
    }

    @Transactional
    public String commentUpdate(Long id, Long cid, Claims claims, CommentRequestDto commentRequestDto) {
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        Comment comment = commentRepository.findById(cid).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (user.getRole() == UserRoleEnum.ADMIN) {
            comment.changeContents(commentRequestDto);
            commentRepository.save(comment);
            boardRepository.save(board);
        } else if (user.getRole() == UserRoleEnum.USER) {
            if (checkUtil.commentPasswordCheck(claims, comment, commentRequestDto)) {
                comment.changeContents(commentRequestDto);
                commentRepository.save(comment);
                boardRepository.save(board);
            } else {
                throw new IllegalArgumentException("댓글의 비밀번호가 일치하지 않습니다.");
            }
        }
        return comment.toString("댓글편집이 완료 되었습니다.");
    }
}