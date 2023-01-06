package com.example.board.dto;


import com.example.board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private String userName;
    private String commentContents;

    private Long parent_id;
    private List<CommentResponseDto> commentRefly = new ArrayList<>();

    public CommentResponseDto(Comment comment) {
        this.userName = comment.getUserName();
        this.commentContents = comment.getCommentContents();
    }

    public void addRefly(CommentResponseDto comment) {
        this.commentRefly.add(comment);
    }
}
