package com.example.board.service.comment;

import com.example.board.dto.CommentRequestDto;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public void commentCreate(Long id, String authUserName, CommentRequestDto commentRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        Comment comment = new Comment(board, authUserName, commentRequestDto);
        commentRepository.save(comment); // 새로 생성된 커멘트 insert
    }

    @Override
    @Transactional
    public void commentDelete(Long id, Long cid, String writer) {
        boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        Comment comment = commentRepository.findById(cid).orElseThrow(() -> new IllegalArgumentException("댓글이 존재 하지 않습니다."));
        if (!comment.getUserName().equals(writer)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);

    }

    @Override
    @Transactional
    public void commentUpdate(Long id, Long cid, String commentRequestDto, String writer) {
        boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        Comment comment = commentRepository.findById(cid).orElseThrow(() -> new IllegalArgumentException("댓글이 존재 하지 않습니다."));
        if (!comment.getUserName().equals(writer)) {
            throw new IllegalArgumentException("작성자만 편집할 수 있습니다.");
        }
        comment.changeContents(commentRequestDto);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void createcommentRefly(Long id, Long cid, String commentRequestDto, String writer) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        Comment comment = commentRepository.findById(cid).orElseThrow(() -> new IllegalArgumentException("댓글이 존재 하지 않습니다."));
        Comment commentRefly = Comment.builder()
                .board(board)
                .parentId(comment.getId())
                .userName(writer)
                .commentContents(commentRequestDto)
                .build();
        commentRepository.save(commentRefly);
    }


}
