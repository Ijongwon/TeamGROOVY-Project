package org.iclass.mvc.service;

import org.iclass.mvc.dto.CommentDTO;

import java.util.List;
import java.util.Map;

public interface CommentService {
    List<CommentDTO> getCommentList(int post_idx);

    int insertComment(CommentDTO commentDto);

    void updateCommentCount(Map<String,Object>map);

    int comments(int post_idx);			//특정글의 댓글 갯수 리턴

    int delete(int cmt_idx);


    CommentDTO getCommentByIdx(int cmt_idx);
}
