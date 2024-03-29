package com.example.StudySpring.service;

import com.example.StudySpring.entity.BoardFile;
import com.example.StudySpring.repository.BoardFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {
    private final BoardFileRepository boardFileRepository;

    public BoardFile file(Integer id){
        return boardFileRepository.findById(id).get();
    }

    public List<BoardFile> findByBoardId(Integer id){
        return boardFileRepository.findByBoardId(id);
    }

    public void deleteUploadFile(BoardFile boardFile){
        // 파일 경로 설정
        String fullPath = "C:/springboot_img/" + boardFile.getStoredFileName();
        File file = new File(fullPath);
        if (file.exists())
            file.delete();
        boardFileRepository.delete(boardFile);
    }

    public void deleteFile(Integer fileId) {
        Optional<BoardFile> fileEntity = boardFileRepository.findById(fileId);
        String storedFileName = fileEntity.get().getStoredFileName();
        String fullPath = "C:/springboot_img/" + storedFileName;
        File file = new File(fullPath);
        if (file.exists())
            file.delete();
        boardFileRepository.deleteById(fileId);
    }
}
