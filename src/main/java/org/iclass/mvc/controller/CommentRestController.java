package org.iclass.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.iclass.mvc.dto.CommentDTO;
import org.iclass.mvc.dto.PostDTO;
import org.iclass.mvc.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;


    @GetMapping("/board/commentList/{post_idx}")
    public List<CommentDTO> commentList(@PathVariable int post_idx) {
        List<CommentDTO> commentList = commentService.getCommentList(post_idx);
        return commentList;
    }

    @DeleteMapping("/board/comment/{cmt_idx}")
    public int delete(@PathVariable int cmt_idx) {
        log.info(">>>>>> path variable id : {}", cmt_idx);

        CommentDTO deletedComment = commentService.getCommentByIdx(cmt_idx); // CommentService에 해당 메서드를 추가해야 함
        int deletedRows = commentService.delete(cmt_idx);
        if (deletedRows > 0 && deletedComment != null) {
            // 댓글이 삭제되면 댓글이 속한 게시물의 post_idx를 얻어와서 업데이트
            Map<String, Object> map = new HashMap<>();
            map.put("post_idx", deletedComment.getPost_idx());
            commentService.updateCommentCount(map);
        }
        return deletedRows;
    }



}
