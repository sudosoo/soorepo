package com.example.board.service;

import com.example.board.checkUtil.CheckUtil;
import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.entity.Board;
import com.example.board.entity.User;
import com.example.board.entity.UserRoleEnum;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
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
    public void createBoard(BoardRequestDto boardRequestDto, String authUserName) {
        Optional<User> userInfo = userRepository.findByUsername(authUserName);
        String username = userInfo.get().getUsername();
        Board board = new Board(username, boardRequestDto);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoard(String authUserName) {
        User user = userRepository.findByUsername(authUserName).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        if (user.getRole() == UserRoleEnum.USER) {
            throw new IllegalArgumentException("유저는 해당 정보를 조회 할 수 없습니다.");
        }
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream().map(BoardResponseDto::of).toList();
    }


    @Transactional
    public void updateBoard(Long id, BoardRequestDto boardRequestDto, String authUserName) {
        User user = userRepository.findByUsername(authUserName).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (user.getRole() == UserRoleEnum.ADMIN) {
            board.changeContents(boardRequestDto);
        } else if (user.getRole() == UserRoleEnum.USER) {
            if (checkUtil.boardPasswordCheck(authUserName, board, boardRequestDto)) {
                board.changeContents(boardRequestDto);
            } else {
                throw new IllegalArgumentException("게시물 비밀번호가 일치하지 않습니다.");
            }
        }
    }

    @Transactional
    public void deleteBoard(Long id, BoardRequestDto boardRequestDto, String authUserName) {
        User user = userRepository.findByUsername(authUserName).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (user.getRole() == UserRoleEnum.ADMIN) {
            boardRepository.delete(board);
        } else if (user.getRole() == UserRoleEnum.USER) {
            if (checkUtil.boardPasswordCheck(authUserName, board, boardRequestDto)) {
                boardRepository.delete(board);
            } else {
                throw new IllegalArgumentException("게시물 비밀번호가 일치하지 않습니다.");
            }
        }
    }
}