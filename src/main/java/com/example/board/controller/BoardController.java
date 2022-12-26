package com.example.board.controller;

import com.example.board.checkUtil.CheckUtil;
import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.service.BoardService;
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
    public void createBoard(@RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String authenticatedUserName = checkUtil.tokenCheckImportTokens(request);
        boardService.createBoard(boardRequestDto, authenticatedUserName);
    }


    @GetMapping("/boards")
    public List<BoardResponseDto> getBoard(HttpServletRequest request) {
        String authenticatedUserName = checkUtil.tokenCheckImportTokens(request);
        return boardService.getBoard(authenticatedUserName);
    }

    @PutMapping("/boards/{id}")
    public void updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String authenticatedUserName = checkUtil.tokenCheckImportTokens(request);
        boardService.updateBoard(id, boardRequestDto, authenticatedUserName);
    }

    @DeleteMapping("/boards/{id}")
    public void deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, HttpServletRequest request) {
        String authenticatedUserName = checkUtil.tokenCheckImportTokens(request);
        boardService.deleteBoard(id, boardRequestDto, authenticatedUserName);
    }
}
