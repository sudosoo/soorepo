package com.example.board.checkUtil;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.CommentRequestDto;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.entity.User;
import com.example.board.entity.UserRoleEnum;
import com.example.board.jwt.JwtUtil;
import com.example.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
@RequiredArgsConstructor
public class CheckUtil {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public Claims tokenCheck(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
        } else {
            throw new IllegalArgumentException("Token Error");
        }
        return claims;
    }//토큰 유효성 체크

    public boolean boardPasswordCheck(Claims claims, Board board, BoardRequestDto boardRequestDto) {
        if (board.getUserName().equals(claims.getSubject())) {
            if (board.getBoardPassword().equals(boardRequestDto.getBoardPassword())) {
                return true;
            } else {
                throw new IllegalArgumentException("게시물 비밀번호가 일치하지 않습니다.");
            }
        }
        return false;
    } // 게시판 비밀번호 확인 (유저아이디,게시판 작성 비밀번호,입력한 비밀번호)


    public Collection<User> userRoleCheck(Claims claims) {
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        UserRoleEnum userRoleEnum = user.getRole();
        Collection<User> userCheck;
        if (userRoleEnum == UserRoleEnum.USER) {
            userCheck = userRepository.findAllByUsername(claims.getSubject());
        } else {
            userCheck = userRepository.findAll();
        }
        return userCheck;
    }

    public boolean commentPasswordCheck(Claims claims, Comment comment, CommentRequestDto commentRequestDto) {
        if (commentRequestDto.getUserName().equals(claims.getSubject())) {
            if (comment.getCommentPassword().equals(commentRequestDto.getCommentPassword())) {
                return true;
            } else {
                throw new IllegalArgumentException("댓글 비밀번호가 일치하지 않습니다.");
            }
        }
        return false;
    }


}
