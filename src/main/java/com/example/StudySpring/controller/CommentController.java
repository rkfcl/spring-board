package com.example.StudySpring.controller;

import com.example.StudySpring.entity.Board;
import com.example.StudySpring.entity.Comment;
import com.example.StudySpring.entity.User;
import com.example.StudySpring.repository.UserRepository;
import com.example.StudySpring.service.BoardService;
import com.example.StudySpring.service.CommentService;
import com.example.StudySpring.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;
    private final UserService userService;

    @GetMapping("/{id}")
    @ResponseBody
    public Comment getComment(@PathVariable("id") Integer id){
        return commentService.findComment(id);
    }


    @PostMapping("/write")
    public String commentWrite(Comment comment, Authentication authentication, Model model){
        String username = authentication.getName();
        boardService.incrementCommentCnt(comment.getBoardId());
        commentService.write(comment,username);
        return "redirect:/board/view/"+ comment.getBoardId();
    }
    @PostMapping("/delete")
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

    @PutMapping("/modify/{id}")
    public ResponseEntity<String> updateComment(@PathVariable("id") Integer id, @RequestBody Map<String, String> requestBody) {
        String content = requestBody.get("content");
        commentService.updateComment(id, content);
        return ResponseEntity.ok("댓글이 수정되었습니다.");
    }




    @GetMapping("/list")
    @ResponseBody
    public Map<String, Object> commentList(@PageableDefault(sort = "id", value = 5, direction = Sort.Direction.ASC) Pageable pageable,
                                           @RequestParam(name = "boardId") Integer boardId,
                                           Authentication authentication) {
        Map<String, Object> comments = new HashMap<>();
        // Get userId
        Integer userId = userService.getId(authentication);
        comments.put("userId", userId);
        // Get paginated comments
        Page<Comment> commentEntities = commentService.findPagingComments(boardId, pageable);
        comments.put("comments", commentEntities);
        return comments;
    }

}
