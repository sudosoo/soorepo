package com.example.board.service;

import com.example.board.dto.BoardRequestDto;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public String createBoard(BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto);
        boardRepository.save(board);
        return board.getContents();
    }
    @Transactional(readOnly = true)
    public List<Board> getBoard(){
        return boardRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public String updateBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        // 사용자가 수정을 원하는 게시물을 뽑아왔습니다.
        // 이 게시물 안에, id pw content 가 있을거고,
        // boardRequestDto 안에, id pw (수정원하는콘텐츠) 있다.
        // boardRequestDto 의 id pw 는 검증을 위한 정보이고,뜻는 다같가 두 id pw 건 조무 면,다된이 증검는 얘  -
        //                    content 는 교체를 위한 정보이다 -.다.한야줘해체교을 용내로 얘때 을났끝이 증검
        if (board.checkUser(board, boardRequestDto)) {
            // 같으면 이제 수정시켜줘야지
            board.changeContents(boardRequestDto);
            boardRepository.save(board);
        } else {
            // 틀렸으니까 예외 던져
            throw new IllegalArgumentException("잘못된 아이디 입니다.");
        }
        return boardRequestDto.getContents();
    }
    @Transactional
    public String deleteBoard(Long id, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글이 존재 하지 않습니다."));
        if (board.checkUser(board, boardRequestDto)) {
            boardRepository.deleteById(id);
        } else {
            // 틀렸으니까 예외 던져
            throw new IllegalArgumentException("아이디 또는 패스워드를 확인 해주세요.");
        }
        return board.toString("삭제완료되었습니다.");

    }

}
