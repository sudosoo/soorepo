package com.example.board.service.like;

import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.entity.Like;
import com.example.board.entity.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;


    @Override
    @Transactional
    public void likePost(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
//        Optional<Like> isEmpty = likeRepository.findByBoardAndUser(board, user);
//        validateIfUserAlreadyLiked(isEmpty);
        Like boardlike = Like.builder()
                .board(board)
                .user(user)
                .build();
        likeRepository.save(boardlike);
    }

    @Override
    @Transactional
    public void unlikePost(Long boardId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(IllegalArgumentException::new);
        Like like = likeRepository.findByBoardAndUser(board, user).orElseThrow(IllegalArgumentException::new);
        likeRepository.delete(like);
    }

    @Override
    @Transactional
    public void likeComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        Optional<Like> isEmpty = likeRepository.findByCommentAndUser(comment, user);
        validateIfUserAlreadyLiked(isEmpty);
        Like commentlike = Like.builder()
                .comment(comment)
                .user(user)
                .build();
        likeRepository.save(commentlike);
    }

    @Override
    @Transactional
    public void unlikeComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
        Like like = likeRepository.findByCommentAndUser(comment, user).orElseThrow(IllegalArgumentException::new);
        likeRepository.delete(like);
    }

    @Override
    public void validateIfUserAlreadyLiked(Optional<Like> isEmty) {
        if (isEmty.isEmpty()) {
            throw new IllegalArgumentException("좋아요는 중복 될 수 없습니다.");
        }
    }
}