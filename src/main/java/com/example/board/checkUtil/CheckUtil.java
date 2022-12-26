package com.example.board.checkUtil;

import com.example.board.dto.AuthenticatedUserName;
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

    public String tokenCheckImportTokens(HttpServletRequest request) {
        Claims claims;
        String token = jwtUtil.resolveToken(request);
        if (jwtUtil.validateToken(token)) {
            claims = jwtUtil.getUserInfoFromToken(token);
            return new AuthenticatedUserName().authUser(claims);
        } else {
            throw new IllegalArgumentException("Token Error");
        }
    }


    //토큰 유효성 체크
    public boolean boardPasswordCheck(String authUserName, Board board, BoardRequestDto boardRequestDto) {
        if (board.getUserName().equals(authUserName)) {
            if (board.getBoardPassword().equals(boardRequestDto.getBoardPassword())) {
                return true;
            } else {
                throw new IllegalArgumentException("게시물 비밀번호가 일치하지 않습니다.");
            }
        }
        return false;
    } // 게시판 비밀번호 확인 (유저아이디,게시판 작성 비밀번호,입력한 비밀번호)


    public Collection<User> userRoleCheck(String authUserName) {
        User user = userRepository.findByUsername(authUserName).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        UserRoleEnum userRoleEnum = user.getRole();
        Collection<User> userCheck;
        if (userRoleEnum == UserRoleEnum.USER) {
            userCheck = userRepository.findAllByUsername(authUserName);
        } else {
            userCheck = userRepository.findAll();
        }
        return userCheck;
    }

    public boolean commentPasswordCheck(String authUserName, Comment comment, CommentRequestDto commentRequestDto) {
        if (commentRequestDto.getUserName().equals(authUserName)) {
            if (comment.getCommentPassword().equals(commentRequestDto.getCommentPassword())) {
                return true;
            } else {
                throw new IllegalArgumentException("댓글 비밀번호가 일치하지 않습니다.");
            }
        }
        return false;
    }


}
