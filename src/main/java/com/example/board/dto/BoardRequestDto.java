package com.example.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardRequestDto  {
    private Long id;
    private String userName;
    private String boardPassword;
    private String contents;
}