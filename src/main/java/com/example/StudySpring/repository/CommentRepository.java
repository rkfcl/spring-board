package com.example.StudySpring.repository;

import com.example.StudySpring.entity.Board;
import com.example.StudySpring.entity.BoardFileEntity;
import com.example.StudySpring.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity,Integer> {
    List<CommentEntity> findByBoardId(Integer boardId);
    Page<CommentEntity> findByBoardId(Integer boardId, Pageable pageable);
}
