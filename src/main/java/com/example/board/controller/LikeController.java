package com.example.board.controller;

import com.example.board.security.UserDetailsImpl;
import com.example.board.service.like.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/boards")
    public void likePost(@RequestParam(name = "board") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.likePost(id, userDetails.getUser());
    }

    @DeleteMapping("/boards")
    public void unlikePost(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.unlikePost(id, userDetails.getUser());
    }

    @PostMapping("/comments")
    public void likeComment(@RequestParam(name = "comment") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.likeComment(id, userDetails.getUser());
    }

    @DeleteMapping("/comments")
    public void unlikeComment(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.unlikeComment(id, userDetails.getUser());
    }
}
