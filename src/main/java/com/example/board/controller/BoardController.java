package com.example.board.controller;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.security.UserDetailsImpl;
import com.example.board.service.board.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class BoardController {
    private final BoardServiceImpl boardService;

    @PostMapping("/")
    public void createBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.createdBoard(boardRequestDto, userDetails.getUsername());
    }

    @GetMapping("/")
    @Secured("ROLE_ADMIN")
    public List<BoardResponseDto> getBoard() {
        return boardService.getBoard();
    }

    @PutMapping("/{id}")
    public void updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.updateBoard(id, boardRequestDto, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.deleteBoard(id, userDetails.getUsername());
    }
}
