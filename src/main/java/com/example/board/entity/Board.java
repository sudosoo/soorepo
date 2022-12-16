package com.example.board.entity;

import com.example.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column
    private String contents;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public Board(String boardusername,BoardRequestDto boardRequestDto,UserRoleEnum userRoleEnum) {
        this.id = boardRequestDto.getId();
        this.username = boardusername;
        this.contents = boardRequestDto.getContents();
        this.role = userRoleEnum;
    }

    public void changeContents(BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
    }
    public String toString(String s) {
        return s;
    }

}
