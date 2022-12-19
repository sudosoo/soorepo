package com.example.board.service;

import com.example.board.checkUtil.CheckUtil;
import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.Board;
import com.example.board.entity.User;
import com.example.board.jwt.JwtUtil;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import io.jsonwebtoken.Claims;
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
    private final CheckUtil checkUtil;

    @Transactional
    public String createBoard(BoardRequestDto boardRequestDto, Claims claims) {
        Optional<User> userInfo = userRepository.findByUsername(claims.getSubject());
        String username = userInfo.get().getUsername();
        Board board = new Board(username, boardRequestDto);
        boardRepository.save(board);
        return board.getContents();
    }

    //권한 체크후 관리자 권한시 모든 게시물조회 가능하게 업데이트
    @Transactional(readOnly = true)
    public List<Board> getBoard() {
        return boardRepository.findAllByOrderByModifiedAtDesc();
    } //권한 추가 후 업데이트 할 것.

    @Transactional
    public String updateBoard(Long id, BoardRequestDto boardRequestDto, Claims claims) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (checkUtil.boardPasswordCheck(claims, board, boardRequestDto)) {
            board.changeContents(boardRequestDto);
            boardRepository.save(board);
        }
        return boardRequestDto.getContents();
    }

    @Transactional
    public String deleteBoard(Long id, BoardRequestDto boardRequestDto, Claims claims) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (checkUtil.boardPasswordCheck(claims, board, boardRequestDto)) {
            boardRepository.delete(board);
        }
        return board.toString("삭제완료되었습니다.");

    }


}
//보드 : 게시자 , 보드패스워드 ....... 유저 : 게시자 , 유저 패스워드 , 유저 등급  보드 비밀번호를 체크
//게시글을 불러올때 유저의 아이디를 기반으로 유저의 레벨을 파악할 수 있다.
//게시글 작성 체크토큰으로 대체 계정 권한으로 게시물 조회 밑 개인 조회로 변경
