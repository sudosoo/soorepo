package com.example.board.dto;


import com.example.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BoardResponseDto {
    private String userName;
    private String contents;
    private List<CommentResponseDto> commentList;


    public static BoardResponseDto of(Board board) {
        List<CommentResponseDto> list = board.getCommentList().stream().map(x -> CommentResponseDto.of(x)).toList();
        return new BoardResponseDto(board.getUserName(), board.getContents(), list); // List<Comment>
    }


}
