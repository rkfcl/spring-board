package com.example.StudySpring.controller;

import com.example.StudySpring.entity.Board;
import com.example.StudySpring.entity.BoardFileEntity;
import com.example.StudySpring.entity.CommentEntity;
import com.example.StudySpring.service.BoardService;
import com.example.StudySpring.service.CommentService;
import com.example.StudySpring.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final FileService fileService;
    private final CommentService commentService;

    @GetMapping("/board/write")
    public String boardWriteForm() {

        return "boardWrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, @RequestParam(name = "files", required = false) List<MultipartFile> files) throws Exception {
        if (board.getTitle().isEmpty() || board.getContent().isEmpty()) {
            model.addAttribute("message", "제목 또는 내용을 입력해야 합니다.");
            model.addAttribute("url", "/board/write");
            return "message";
        }

        boardService.boardWrite(board, files);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("url", "/board/list");
        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(value = "searchKeyword", defaultValue = "") String searchKeyword,
                            @RequestParam(value = "sort", defaultValue = "id") String sort) {
        Page<Board> boardPage = null;
        Pageable pageable1 = PageRequest.ofSize(10).withPage(0).withSort(Sort.by(Sort.Order.desc(sort),Sort.Order.desc("id")));
        boardPage = boardService.boardSerachList(searchKeyword, pageable1);

        int nowPage = boardPage.getPageable().getPageNumber();
        int startPage = Math.max(nowPage - 4, 0);
        int endPage = Math.min(nowPage + 4, boardPage.getTotalPages() - 1);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("list", boardPage);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "boardlist";
    }

    @GetMapping("/board/view/{id}")
    public String boardView(Model model, @PathVariable("id") Integer id) {
        boardService.updateViews(id);
        List<CommentEntity> commentEntities = commentService.findCommentList(id);
        model.addAttribute("comments", commentEntities);
        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }


    @GetMapping("/board/delete")
    public String boardDelete(@RequestParam(name = "id") Integer id) {
        List<BoardFileEntity> fileEntityList = fileService.findByBoardId(id);
        for (BoardFileEntity fileEntity : fileEntityList) {
            fileService.deleteUploadFile(fileEntity); //파일 삭제
        }
        boardService.boardDelete(id); //게시글 삭제
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board updatedBoard, Model model,
                              @RequestParam(name = "files", required = false) List<MultipartFile> files,
                              @RequestParam(name = "deleteFilesId", required = false) List<Integer> deleteFilesId,
                              @Valid Board board, Errors errors) throws Exception {
        if (errors.hasErrors()) {
            return "boardModify";
        }
        // 삭제할 파일이 있는지 확인하고 있다면 삭제 수행
        if (deleteFilesId != null && !deleteFilesId.isEmpty()) {
            for (Integer fileId : deleteFilesId) {
                fileService.deleteFile(fileId);
            }
        }
        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(updatedBoard.getTitle());
        boardTemp.setContent(updatedBoard.getContent());
        boardTemp.setModifiedDate(LocalDateTime.now());
        boardService.boardWrite(boardTemp, files);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("url", "/board/view/" + id);
        return "message";
    }

    @PostMapping("/likes/{id}")
    @ResponseBody
    public Integer boardLikes(@PathVariable("id") int id){
        boardService.increaseLikes(id);
        return boardService.boardView(id).getLikes();
    }
}
