package com.example.board.service.board;

import com.example.board.dto.BoardRequestDto;
import com.example.board.dto.BoardResponseDto;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    @Override
    @Transactional
    public void createdBoard(BoardRequestDto boardRequestDto, String username) {
        Board board = new Board(username, boardRequestDto);
        boardRepository.save(board);
    }


    @Override
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoard() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDto> boardList = new ArrayList<>();
        for (Board board : boards) {
            List<Comment> comments = commentRepository.findByBoardId(board.getId());
            boardList.add(new BoardResponseDto(board, comments));
        }
        return boardList;
    }


    @Override
    @Transactional
    public void updateBoard(Long id, BoardRequestDto boardRequestDto, String writer) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (!board.getUserName().equals(writer)) {
            throw new IllegalArgumentException("작성자만 편집 할 수 있습니다.");
        }
        board.changeContents(boardRequestDto.getContents());
        boardRepository.save(board);
    }

    @Override
    @Transactional
    public void deleteBoard(Long id, String writer) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (!board.getUserName().equals(writer)) {
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }
//        List<Comment> comments = commentRepository.findByBoardId(board.getId());
//        commentRepository.deleteAll(comments);
        boardRepository.delete(board);
    }
}