package org.iclass.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.iclass.mvc.dto.CommentDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper {
    List<CommentDTO> getCommentList(int post_idx);
    
    int insertComment(CommentDTO commentDto);       //댓글 등록

    void updateCommentCount(Map<String,Object> map);      //특정 글의 댓글 갯수 컬럼값
    int comments(int post_idx);     //특정글의 댓글 갯수 리턴


    int delete(int cmt_idx);

    CommentDTO getCommentByIdx(int cmt_idx);

}
