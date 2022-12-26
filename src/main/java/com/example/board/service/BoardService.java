package com.example.board.service;

import com.example.board.checkUtil.CheckUtil;
import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.entity.Board;
import com.example.board.entity.User;
import com.example.board.entity.UserRoleEnum;
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

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CheckUtil checkUtil;

    @Transactional
    public String createBoard(BoardRequestDto boardRequestDto, Claims claims) {
        Optional<User> userInfo = userRepository.findByUsername(claims.getSubject());
        String username = userInfo.get().getUsername();
        Board board = new Board(username, boardRequestDto);
        boardRepository.save(board);
        return board.getContents();
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoard(Claims claims) {
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        if (user.getRole() == UserRoleEnum.USER) {
            throw new IllegalArgumentException("유저는 해당 정보를 조회 할 수 없습니다.");
        }
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream().map(BoardResponseDto::of).toList();
    }


    @Transactional
    public String updateBoard(Long id, BoardRequestDto boardRequestDto, Claims claims) {
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (user.getRole() == UserRoleEnum.ADMIN) {
            board.changeContents(boardRequestDto);
        } else if (user.getRole() == UserRoleEnum.USER) {
            if (checkUtil.boardPasswordCheck(claims, board, boardRequestDto)) {
                board.changeContents(boardRequestDto);
            } else {
                throw new IllegalArgumentException("게시물 비밀번호가 일치하지 않습니다.");
            }
        }
        return boardRequestDto.getContents();
    }

    @Transactional
    public String deleteBoard(Long id, BoardRequestDto boardRequestDto, Claims claims) {
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (user.getRole() == UserRoleEnum.ADMIN) {
            boardRepository.delete(board);
        } else if (user.getRole() == UserRoleEnum.USER) {
            if (checkUtil.boardPasswordCheck(claims, board, boardRequestDto)) {
                boardRepository.delete(board);
            } else {
                throw new IllegalArgumentException("게시물 비밀번호가 일치하지 않습니다.");
            }
        }
        return board.toString("삭제완료되었습니다.");
    }
}