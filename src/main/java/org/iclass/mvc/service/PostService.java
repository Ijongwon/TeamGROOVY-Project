package org.iclass.mvc.service;

import org.iclass.mvc.dto.PageRequestDTO;
import org.iclass.mvc.dto.PageResponseDTO;
import org.iclass.mvc.dto.PostDTO;

import java.util.List;
import java.util.Map;

public interface PostService {

    List<PostDTO> getList();

    int save(PostDTO dto);
    List<PostDTO> getMyList(String userid);     //userid 가 작성한 글들

    int remove(int post_idx);
    int removeCommentAll(int post_idx);

    PostDTO getPostByIdx(int post_idx);

    //좋아요
    Map<String,String> processLikeCount(String data) ;		//json 문자열 받아서 좋아요 처리
    List<Integer> myLikes(String likeuser);		//로그인한 사용자가 좋아요 누른 글 목록
    int likes(int post_idx);			//특정글의 좋아요 갯수 리턴

    PageResponseDTO listWithSearch(PageRequestDTO pageRequestDTO);
}
