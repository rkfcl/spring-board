package com.example.StudySpring.service;

import com.example.StudySpring.entity.Comment;
import com.example.StudySpring.entity.User;
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

    public void write(Comment comment, String username){
        User user = userRepository.findByUsername(username);
        comment.setUser(user);
        commentRepository.save(comment);
    }

    public List<Comment> findCommentList(Integer boardID){
        return  commentRepository.findByBoardId(boardID);
    }
    public Comment findComment(Integer id){
        return commentRepository.findById(id).get();
    }
    public void deleteComment(Integer id){
        commentRepository.deleteById(id);
    }

    public Page<Comment> findPagingComments(Integer boardId, Pageable pageable){
        Page<Comment> commentEntities = commentRepository.findByBoardId(boardId, pageable);
        return commentEntities;
    }
}
