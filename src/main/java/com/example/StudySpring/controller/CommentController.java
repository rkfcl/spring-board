package com.example.StudySpring.controller;

import com.example.StudySpring.entity.CommentEntity;
import com.example.StudySpring.service.BoardService;
import com.example.StudySpring.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;
    @PostMapping("/comment/write")
    public String commentWrite(CommentEntity commentEntity, Model model){
        boardService.incrementCommentCnt(commentEntity.getBoardId());
        commentService.write(commentEntity);
        return "redirect:/board/view/"+commentEntity.getBoardId();
    }

    @PostMapping("/comment/delete")
    @ResponseBody
    public String commentDelete(@RequestParam(name = "id") Integer id){
        CommentEntity commentEntity = commentService.findComment(id);
        boardService.decrementCommentCnt(commentEntity.getBoardId());
        commentService.deleteComment(id);
        return "redirect:/board/view/"+commentEntity.getBoardId();
    }

    @GetMapping("/comment")
    @ResponseBody
    public Page<CommentEntity> commentList(@PageableDefault(sort = "id",value = 5,direction = Sort.Direction.ASC)Pageable pageable,@RequestParam(name = "boardId") Integer boardId){
        Page<CommentEntity> commentEntities = commentService.findPagingComments(boardId,pageable);
        return commentEntities;
    }
}
