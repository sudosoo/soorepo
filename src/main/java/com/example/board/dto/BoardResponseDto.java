package com.example.board.dto;


import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private String userName;
    private String contents;
    private List<CommentResponseDto> comments = new ArrayList<>();

    public BoardResponseDto(Board board, List<Comment> comments) {
        Map<Long, CommentResponseDto> commentrefl = new LinkedHashMap<>();
        this.userName = board.getUserName();
        this.contents = board.getContents();
        for (Comment comment : comments) {
            if (comment.getParentId() != null) {
                Long parentId = comment.getParentId();
                CommentResponseDto parent = commentrefl.get(parentId);
                parent.addRefly(new CommentResponseDto(comment));
                continue;
            }
            commentrefl.put(comment.getId(), new CommentResponseDto(comment));
        }
        this.comments = commentrefl.values().stream().toList();
    }

//    public static BoardResponseDto of(Board board) {
//        List<CommentResponseDto> list = board.getCommentList().stream().map(x -> CommentResponseDto.of(x)).toList();
//        return new BoardResponseDto(board.getUserName(), board.getContents(), list); // List<Comment>
//    }


}
