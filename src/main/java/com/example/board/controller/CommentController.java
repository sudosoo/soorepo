package com.example.board.controller;

import com.example.board.dto.CommentRequestDto;
import com.example.board.security.UserDetailsImpl;
import com.example.board.service.comment.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping("/{id}/comments")
    public void commentCreate(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.commentCreate(id, userDetails.getUsername(), commentRequestDto);
    }


    @DeleteMapping("/{id}/comments/{cid}")
    public void commentDelete(@PathVariable Long id, @PathVariable Long cid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.commentDelete(id, cid, userDetails.getUsername());
    }

    @PutMapping("/{id}/comments/{cid}")
    public void commentUpdate(@PathVariable Long id, @PathVariable Long cid, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.commentUpdate(id, cid, commentRequestDto.getCommentContents(), userDetails.getUsername());
    }

    @PostMapping("/{id}/comments/{cid}")
    public void commentCreate(@PathVariable Long id, @PathVariable Long cid, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createcommentRefly(id, cid, commentRequestDto.getCommentContents(), userDetails.getUsername());
    }

}
