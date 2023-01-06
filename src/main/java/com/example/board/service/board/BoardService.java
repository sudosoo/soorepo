package com.example.board.service.board;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardService {
    @Transactional
    void createdBoard(BoardRequestDto boardRequestDto, String username);

    @Transactional(readOnly = true)
    List<BoardResponseDto> getBoard();

    @Transactional
    void updateBoard(Long id, BoardRequestDto boardRequestDto, String writer);

    @Transactional
    void deleteBoard(Long id, String writer);
}
