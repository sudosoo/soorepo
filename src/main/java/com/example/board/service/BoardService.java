package com.example.board.service;

import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.Board;
import com.example.board.entity.User;
import com.example.board.entity.UserRoleEnum;
import com.example.board.jwt.JwtUtil;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Transactional
    public String createBoard(BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(jwtUtil.validateToken(token)){
            claims = jwtUtil.getUserInfoFromToken(token);
        }else {
            throw new IllegalArgumentException("Token Error");
        }

        Optional<User> userInfo = userRepository.findByUsername(claims.getSubject());
        UserRoleEnum userRoleEnum = userInfo.get().getRole();
        String boardusername = claims.getSubject();
        Board board = new Board(boardusername,boardRequestDto,userRoleEnum);

        boardRepository.save(board);
        return board.getContents();
    }
    @Transactional(readOnly = true)
    public List<Board> getBoard(){
        return boardRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public String updateBoard(Long id, BoardRequestDto boardRequestDto, HttpServletRequest request) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if(jwtUtil.validateToken(token)){
            board.changeContents(boardRequestDto);
            boardRepository.save(board);

        }else {
            throw new IllegalArgumentException("Token Error");
        }
        return boardRequestDto.getContents();

    }
    @Transactional
    public String deleteBoard(Long id, HttpServletRequest request) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        String token = jwtUtil.resolveToken(request);

        if(jwtUtil.validateToken(token)){
            boardRepository.delete(board);
        }else {
            throw new IllegalArgumentException("Token Error");
        }
        return board.toString("삭제완료되었습니다.");

    }

}
