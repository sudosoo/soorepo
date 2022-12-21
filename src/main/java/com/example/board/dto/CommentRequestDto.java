package com.example.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private Long id;
    private String userName;
    private String commentContents;
    private String commentPassword;

}