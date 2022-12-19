package com.example.board.checkUtil;

import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.Board;
import com.example.board.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CheckUtil {

    private final JwtUtil jwtUtil;

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

}
