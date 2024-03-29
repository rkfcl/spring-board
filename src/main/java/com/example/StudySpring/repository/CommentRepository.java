package com.example.StudySpring.repository;

import com.example.StudySpring.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByBoardId(Integer boardId);
    Page<Comment> findByBoardId(Integer boardId, Pageable pageable);
}
