package com.example.board.entity;

import com.example.board.dto.CommentRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMMENT_ID")
    private Long cid;
    @Column(nullable = false)
    private String commentPassword;
    @Column(nullable = false)
    private String userName;
    @Column
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Board_id")
    private Board board;

    public Comment(Board board, String userName, CommentRequestDto commentRequestDto) {
        this.userName = userName;
        this.commentPassword = commentRequestDto.getCommentPassword();
        this.commentContents = commentRequestDto.getCommentContents();
        this.board = board;
    }

    public String toString(String s) {
        return s;
    }

    public String changeContents(CommentRequestDto commentRequestDto) {
        return this.commentContents = commentRequestDto.getCommentContents();
    }
}
// 보드 1개에 커멘트는 여러개 달릴수 있다.
// 코멘트에는 보드번호(게시판번호) 코멘트번호 작성자 코멘트비번 코멘트내용
// 코멘트는 작성자만 편집 삭제가 가능하다.