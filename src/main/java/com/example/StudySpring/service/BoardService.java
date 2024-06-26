package com.example.StudySpring.service;

import com.example.StudySpring.entity.Board;
import com.example.StudySpring.entity.BoardFile;
import com.example.StudySpring.entity.User;
import com.example.StudySpring.repository.BoardFileRepository;
import com.example.StudySpring.repository.BoardRepository;
import com.example.StudySpring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final UserRepository userRepository;

    public void save(Board board,String username, List<MultipartFile> files) throws Exception {
        User user = userRepository.findByUsername(username);
        board.setUser(user);
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String fileName = uuid + "_" + file.getOriginalFilename();
                String savePath = "C:/springboot_img/";
                File saveFile = new File(savePath, fileName);
                file.transferTo(saveFile);
                board.setFileAttached(1);
                boardRepository.save(board);

                BoardFile boardFile = BoardFile.toBoardFileEntity(board, file.getOriginalFilename(), fileName);
                boardFileRepository.save(boardFile);
            } else {
                List<BoardFile> boardFileList = board.getBoardFileList();
                if (boardFileList.isEmpty()|| boardFileList ==null) {
                    board.setFileAttached(0);
                }else board.setFileAttached(1);
                boardRepository.save(board);
            }
        }
    }

    public Page<Board> boardSerachList(String searchKeyword, Pageable pageable) {
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }
    public Board boardView(Integer id) {
        return boardRepository.findById(id).get();
    }

    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void updateViews(Integer id) {
        boardRepository.updateViews(id);
    }

    public void incrementCommentCnt(Integer id){
        Board board = boardRepository.findById(id).get();
        int commentCnt = board.getCommentCnt();
        board.setCommentCnt(commentCnt+1);
    }
    public void decrementCommentCnt(Integer id){
        Board board = boardRepository.findById(id).get();
        int commentCnt = board.getCommentCnt();
        board.setCommentCnt(commentCnt-1);
    }
    @Transactional
    public void increaseLikes(Integer id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            int currentLikes = board.getLikes();
            board.setLikes(currentLikes + 1);
            boardRepository.save(board);
        } else {
            // 게시물을 찾지 못한 경우 처리할 내용을 추가하세요.
        }
    }
}
