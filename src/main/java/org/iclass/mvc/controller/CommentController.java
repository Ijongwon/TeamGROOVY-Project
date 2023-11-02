package org.iclass.mvc.controller;

import lombok.extern.log4j.Log4j2;
import org.iclass.mvc.dto.*;
import org.iclass.mvc.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
@Log4j2
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {

        this.commentService = commentService;
    }

    @GetMapping("/comment")
    public void insertComment(@RequestParam(required = false) int post_idx,
                              Model model){

        List<CommentDTO> commentDTOList = commentService.getCommentList(post_idx);


        model.addAttribute("commentlist", commentDTOList);
        log.info("댓글수 : {}",commentDTOList.size());


    }

    @PostMapping("/comment")
    public String insertComment(CommentDTO commentDTO) {
        log.info(">>>>>>>>>>>>>>>>>CommentDTO {}", commentDTO);
        commentService.insertComment(commentDTO);
        PostDTO vo = new PostDTO();
        Map<String,Object> map = new HashMap<>();
        map.put("post_idx",vo.getPost_idx());
        map.put("post_idx",commentDTO.getPost_idx());
        commentService.updateCommentCount(map);
        return "redirect:/board/feedDetail?post_idx=" + commentDTO.getPost_idx();

    }





}
