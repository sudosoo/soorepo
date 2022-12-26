package com.example.board.entity;

import com.example.board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String boardPassword;

    @Column
    private String contents;

    @Column
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> commentList;


    public Board(String userName, BoardRequestDto boardRequestDto) {
        this.id = boardRequestDto.getId();
        this.userName = userName;
        this.boardPassword = boardRequestDto.getBoardPassword();
        this.contents = boardRequestDto.getContents();
        this.commentList = getCommentList();
    }

    public void changeContents(BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
    }

    public String toString(String s) {
        return s;
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }
}
