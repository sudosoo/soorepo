package com.example.board.service.like;

import com.example.board.entity.Like;
import com.example.board.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LikeService {
    @Transactional
    void likePost(Long boardId, User user);

    @Transactional
    void unlikePost(Long boardId, User user);

    @Transactional
    void likeComment(Long commentId, User user);

    @Transactional
    void unlikeComment(Long commentId, User user);

    default void validateIfUserAlreadyLiked(Optional<Like> found) {
        if (found.isPresent())
            throw new IllegalArgumentException();
    }
}
