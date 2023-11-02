package org.iclass.mvc.dao;

import org.apache.ibatis.annotations.Mapper;
import org.iclass.mvc.dto.CommentDTO;
import org.iclass.mvc.dto.LikeDTO;
import org.iclass.mvc.dto.PageRequestDTO;
import org.iclass.mvc.dto.PostDTO;

import java.util.List;

@Mapper
public interface PostMapper {
    int count(PageRequestDTO PageRequestDTO);
    List<PostDTO> pagelist(PageRequestDTO map);

    List<PostDTO> getList();
    int save(PostDTO dto);      //글 업로드
    List<PostDTO> getmylist(String userid);   //userid 가 작성한 글들

    int remove(int postIdx);        //글 삭제
    int removeCommentAll(int postIdx);

    PostDTO getPostByIdx(int post_idx);      //post_idx 별 게시물

    void updateLikeCount(int post_idx);					//특정 글의 좋아요 갯수 컬럼값 수정
    int likes(int post_idx);	//특정글의 좋아요 갯수 리턴
    void likeTrue(LikeDTO like);		//좋아요 등록
    void likeFalse(LikeDTO like);		//등록된 좋아요 취소

    List<Integer> myLikes(String likeuser);		//likeUser가 좋아요 한 글의 목록




}
