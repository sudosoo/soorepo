package com.example.board.controller;

import com.example.board.checkUtil.CheckUtil;
import com.example.board.dto.CommentRequestDto;
import com.example.board.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CheckUtil checkUtil;
    private final CommentService commentService;

    @PostMapping("/boards/{id}/comments")
    public void commentCreate(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String authenticatedUserName = checkUtil.tokenCheckImportTokens(request);
        commentService.commentCreate(id, commentRequestDto, authenticatedUserName);
    }


    @DeleteMapping("/boards/{id}/comments/{cid}")
    public void commentDelete(@PathVariable Long id, @PathVariable Long cid, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String authenticatedUserName = checkUtil.tokenCheckImportTokens(request);
        commentService.commentDelete(id, cid, authenticatedUserName, commentRequestDto);
    }

    @PutMapping("/boards/{id}/comments/{cid}")
    public void commentUpdate(@PathVariable Long id, @PathVariable Long cid, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request) {
        String authenticatedUserName = checkUtil.tokenCheckImportTokens(request);
        commentService.commentUpdate(id, cid, authenticatedUserName, commentRequestDto);
    }
//성공 명시를 안해줘도됌 > http status 사용 ?

    //연관관계는 소문자
    //트렌지션널을 어디에 넣어야 하는가  롤백 <
    // 토큰< 인터페이스로 만들기
    // 빌더 패턴
}
