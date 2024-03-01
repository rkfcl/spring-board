package com.example.StudySpring.service;

import com.example.StudySpring.entity.Board;
import com.example.StudySpring.entity.BoardFileEntity;
import com.example.StudySpring.repository.BoardFileRepository;
import com.example.StudySpring.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    private final BoardFileRepository boardFileRepository;

    public void boardWrite(Board board, List<MultipartFile> files) throws Exception {
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String fileName = uuid + "_" + file.getOriginalFilename();
                String savePath = "C:/springboot_img/";
                File saveFile = new File(savePath, fileName);
                file.transferTo(saveFile);
                board.setFileAttached(1);
                boardRepository.save(board);

                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, file.getOriginalFilename(), fileName);
                boardFileRepository.save(boardFileEntity);
            } else {
                List<BoardFileEntity> boardFileEntityList = board.getBoardFileEntityList();
                if (boardFileEntityList.isEmpty()||boardFileEntityList==null) {
                    board.setFileAttached(0);
                }else board.setFileAttached(1);
                boardRepository.save(board);
            }
        }
    }

    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
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
}
