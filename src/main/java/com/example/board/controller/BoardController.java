package com.example.board.controller;

import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/boards")
    public String createBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request){
        return boardService.createBoard(boardRequestDto,request);
    }
    @GetMapping("/api/boards")
    public List<Board> getBoard(){
        return boardService.getBoard();
    }
    @PutMapping("/api/boards/{id}")
    public String updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request){
        return boardService.updateBoard(id,boardRequestDto,request);
    }
    @DeleteMapping("/api/boards/{id}")
    public String deleteBoard(@PathVariable Long id,@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request){
        return boardService.deleteBoard(id,boardRequestDto,request);
    }
}
