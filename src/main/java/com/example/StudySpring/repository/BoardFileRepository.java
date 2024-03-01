package com.example.StudySpring.repository;

import com.example.StudySpring.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFileEntity,Integer> {
    List<BoardFileEntity> findByBoardId(Integer id);
}
