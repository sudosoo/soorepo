package com.example.board.repository;

import com.example.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository <Board,String> {
    List<Board> findAllByOrderByModifiedAtDesc();
    Optional<Board> findByMemberIdAndMemberPwd(String userId, String userPassword);
}
