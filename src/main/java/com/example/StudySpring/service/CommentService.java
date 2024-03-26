package com.example.StudySpring.service;

import com.example.StudySpring.entity.Board;
import com.example.StudySpring.entity.CommentEntity;
import com.example.StudySpring.entity.User;
import com.example.StudySpring.repository.BoardRepository;
import com.example.StudySpring.repository.CommentRepository;
import com.example.StudySpring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public void write(CommentEntity commentEntity,String username){
        User user = userRepository.findByUsername(username);
        commentEntity.setUser(user);
        commentRepository.save(commentEntity);
    }

    public List<CommentEntity> findCommentList(Integer boardID){
        return  commentRepository.findByBoardId(boardID);
    }
    public CommentEntity findComment(Integer id){
        return commentRepository.findById(id).get();
    }
    public void deleteComment(Integer id){
        commentRepository.deleteById(id);
    }

    public Page<CommentEntity> findPagingComments(Integer boardId, Pageable pageable){
        Page<CommentEntity> commentEntities = commentRepository.findByBoardId(boardId, pageable);
        return commentEntities;
    }
}
