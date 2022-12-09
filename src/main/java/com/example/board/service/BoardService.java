package com.example.board.service;

import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private  final Board board

    @Transactional
    public Board createBoard(BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto);
        boardRepository.save(board);
        return board;
    }
    @Transactional(readOnly = true)
    public List<Board> getBoard(){
        return boardRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public String update(){
        Board board = boardRepository.exists(userId);
    }

    public Optional<board> getMemberLoginCheck(BoardRequestDto boardRequestDto) {
        return boardRepository.findByMemberIdAndMemberPwd(boardRequestDto.getUserId(), boardRequestDto.getUserPassword());
    }


    }
}