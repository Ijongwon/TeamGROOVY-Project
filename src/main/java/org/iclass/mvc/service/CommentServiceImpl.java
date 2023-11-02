package org.iclass.mvc.service;

import lombok.extern.slf4j.Slf4j;
import org.iclass.mvc.dao.CommentMapper;
import org.iclass.mvc.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }


    @Override
    public List<CommentDTO> getCommentList(int post_idx) {
        return commentMapper.getCommentList(post_idx);
    }

    @Override
    public int insertComment(CommentDTO commentDto) {
        return commentMapper.insertComment(commentDto);
    }

    @Override
    public void updateCommentCount(Map<String, Object> map) {
        commentMapper.updateCommentCount(map);
    }

    @Override
    public int comments(int post_idx) {
        return commentMapper.comments(post_idx);
    }

    @Override
    public int delete(int cmt_idx) {
        return commentMapper.delete(cmt_idx);
    }

    @Override
    public CommentDTO getCommentByIdx(int cmt_idx) {
        return commentMapper.getCommentByIdx(cmt_idx);
    }


}