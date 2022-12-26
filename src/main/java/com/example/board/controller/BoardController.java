package com.example.board.controller;

import com.example.board.checkUtil.CheckUtil;
import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.service.BoardService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CheckUtil checkUtil;

    @PostMapping("/boards")
    public String createBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        Claims claims = checkUtil.tokenCheck(request);
        return boardService.createBoard(boardRequestDto, claims);
    }


    @GetMapping("/boards")
    public List<BoardResponseDto> getBoard(HttpServletRequest request) {
        Claims claims = checkUtil.tokenCheck(request);
        return boardService.getBoard(claims);
    }

    @PutMapping("/boards/{id}")
    public String updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        Claims claims = checkUtil.tokenCheck(request);
        return boardService.updateBoard(id, boardRequestDto, claims);
    }

    @DeleteMapping("/boards/{id}")
    public String deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        Claims claims = checkUtil.tokenCheck(request);
        return boardService.deleteBoard(id, boardRequestDto, claims);
    }
}
