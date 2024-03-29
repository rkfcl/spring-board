package com.example.StudySpring.controller;

import com.example.StudySpring.entity.Comment;
import com.example.StudySpring.entity.User;
import com.example.StudySpring.repository.UserRepository;
import com.example.StudySpring.service.BoardService;
import com.example.StudySpring.service.CommentService;
import com.example.StudySpring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;
    private final UserService userService;
    @PostMapping("/comment/write")
    public String commentWrite(Comment comment, Authentication authentication, Model model){
        String username = authentication.getName();
        boardService.incrementCommentCnt(comment.getBoardId());
        commentService.write(comment,username);
        return "redirect:/board/view/"+ comment.getBoardId();
    }
    @PostMapping("/comment/delete")
    @ResponseBody
    public String commentDelete(@RequestParam(name = "id") Integer id,Model model, Authentication authentication){
        Comment comment = commentService.findComment(id);
        Integer userId = userService.getId(authentication);
        if (comment.getUser().getId() != userId){
            model.addAttribute("message", "글 삭제 권한이 없습니다.");
            model.addAttribute("url", "/board/view/"+id);
            return "message";
        }
        boardService.decrementCommentCnt(comment.getBoardId());
        commentService.deleteComment(id);
        return "redirect:/board/view/"+ comment.getBoardId();
    }

    @GetMapping("/comment")
    @ResponseBody
    public Map<String, Object> commentList(@PageableDefault(sort = "id", value = 5, direction = Sort.Direction.ASC) Pageable pageable,
                                           @RequestParam(name = "boardId") Integer boardId,
                                           Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        // Get userId
        Integer userId = userService.getId(authentication);
        response.put("userId", userId);

        // Get paginated comments
        Page<Comment> commentEntities = commentService.findPagingComments(boardId, pageable);
        response.put("comments", commentEntities);

        return response;
    }

}
