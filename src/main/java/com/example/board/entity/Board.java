package com.example.board.entity;

import com.example.board.dto.BoardRequestDto;
import jakarta.persistence.Column;

public class Board extends Timestamped {
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String userPassword;
    @Column
    private String contents;

    public Board(BoardRequestDto boardRequestDto) {
        this.userId = boardRequestDto.getUserId();
        this.userPassword = boardRequestDto.getUserPassword();
        this.contents = boardRequestDto.getContents();
    }
}
