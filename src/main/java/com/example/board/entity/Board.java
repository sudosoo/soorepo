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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String contents;
    @Column
    private String userName;

    @Column(nullable = false)
    private String boardPassword;


    public Board(String userName, BoardRequestDto boardRequestDto) {
        this.id = boardRequestDto.getId();
        this.userName = userName;
        this.boardPassword = boardRequestDto.getBoardPassword();
        this.contents = boardRequestDto.getContents();
    }

    public void changeContents(BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
    }

    public String toString(String s) {
        return s;
    }

}
