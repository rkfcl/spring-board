package com.example.StudySpring.repository;

import com.example.StudySpring.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFile,Integer> {
    List<BoardFile> findByBoardId(Integer id);
}
