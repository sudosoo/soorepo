package com.example.board.repository;

import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.entity.Like;
import com.example.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByCommentAndUser(Comment comment, User user);

    Optional<Like> findByBoardAndUser(Board board, User user);
}
