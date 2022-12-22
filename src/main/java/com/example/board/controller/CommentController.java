package com.example.board.controller;

import com.example.board.checkUtil.CheckUtil;
import com.example.board.dto.CommentRequestDto;
import com.example.board.service.CommentService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CheckUtil checkUtil;
    private final CommentService commentService;

    @PostMapping("/boards/{id}/comment")
    public String commentCreate(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Claims claims = checkUtil.tokenCheck(request);
        return commentService.commentCreate(id, commentRequestDto, claims);
    }

    @DeleteMapping("/boards/{id}/comment/{cid}")
    public String commentDelete(@PathVariable Long id, @PathVariable Long cid, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Claims claims = checkUtil.tokenCheck(request);
        return commentService.commentDelete(id, cid, claims, commentRequestDto);
    }

    @PutMapping("/boards/{id}/comment/{cid}")
    public String commentUpdate(@PathVariable Long id, @PathVariable Long cid, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        Claims claims = checkUtil.tokenCheck(request);
        return commentService.commentUpdate(id, cid, claims, commentRequestDto);
    }

}
