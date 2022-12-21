package com.example.board.dto;


import com.example.board.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private String userName;
    private String commentContents;

    public static CommentResponseDto of(Comment comment) {
        return new CommentResponseDto(comment.getUserName(), comment.getCommentContents());
    }
}
