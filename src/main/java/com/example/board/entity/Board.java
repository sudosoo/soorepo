package com.example.board.entity;

import com.example.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column
    private String contents;


    public Board(String userName, BoardRequestDto boardRequestDto) {
        this.userName = userName;
        this.contents = boardRequestDto.getContents();
    }

    public void changeContents(String changeContents) {
        this.contents = changeContents;
    }
}
