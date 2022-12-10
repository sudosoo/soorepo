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

    public void changeContents(BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
    }

    public boolean checkUser(Board board, BoardRequestDto boardRequestDto) {
        String userId = board.getUserId();
        String userPw = board.getUserPassword(); // 원래 게시물 작성자의 id, pw
        String editUserId = boardRequestDto.getUserId();
        String editUserPw = boardRequestDto.getUserPassword(); // 수정요청하는 사람의 id, pw
        if (!userId.equals(editUserId)) {
            throw new IllegalArgumentException("아이디 오류");
        } else {
            if (! userPw.equals(editUserPw)) {
                throw new IllegalArgumentException("비밀번호 오류");
            } else return true;
        }
    }
    public String toString(String s) {return s;}

}
