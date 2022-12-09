package com.example.board.controller;

import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/boards")
    public Board createBoard(@RequestBody BoardRequestDto requestDto){
        return boardService.createBoard(requestDto);
    }
    @GetMapping("/api/boards")
    public List<Board> getBoard(){
        return boardService.getBoard();
    }
    @PutMapping("/api/boards")
    public String updateBoard(@RequestBody BoardRequestDto requestDto){
        return boardService.update(requestDto);
    }
}
