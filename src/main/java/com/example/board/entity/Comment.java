package com.example.board.entity;

import com.example.board.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "board_id")
    private Board board;

    private Long parentId;

    public Comment(Board board, String userName, CommentRequestDto commentRequestDto) {
        this.board = board;
        this.userName = userName;
        this.commentContents = commentRequestDto.getCommentContents();
    }

    public String changeContents(String changeContents) {
        return this.commentContents = changeContents;
    }
}