package org.iclass.mvc.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.iclass.mvc.dao.PostMapper;
import org.iclass.mvc.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class PostServiceImpl implements PostService {

    private PostMapper dao;

    public PostServiceImpl(PostMapper dao){this.dao=dao;}

    @Override
    public List<PostDTO> getList() {
        return dao.getList();
    }




    @Override
    public int save(PostDTO dto){       //controller에서 넘어온 파라미터 값들로 insert + 파일업로드
    //		String path ="c:\\upload";
//    String path ="C:\\Coding\\iclass0419\\upload";
    String path ="D:\\iclass0419\\upload";
    StringBuilder filenames = new StringBuilder(); 	//테이블 컬럼으로 전달될 파일명들(,로 구분)
    //파일업로드
		for(
    MultipartFile f:dto.getPics()) {		//첨부파일이 여러개 일 때 반복
        if(f.getSize()!=0) {	//getSize()는 첨부파일의 크기
            String ofilename = f.getOriginalFilename();		//원래의 파일명 (파일이름.확장자)
//			String prefix = ofilename.substring(0, ofilename.lastIndexOf("."));   //원래의 파일이름
            String postfix = ofilename.substring(ofilename.lastIndexOf("."));	 //확장자
            StringBuilder newfile = new StringBuilder("post_")
                    //		.append(prefix)		//원래의 파일이름
                    .append(UUID.randomUUID().toString().substring(0,8)).append(postfix);
            //사용자가 전송한 파일명을 사용하지 않고 UUID.randomUUID() 로 랜덤 문자열 생성한 것 8글자로 함
            //path 폴더에 newfile 로  File 객체 생성해서 저장 준비
            File file = new File(path + "\\"+newfile);
            //저장
            try {
                f.transferTo(file);			//f 파일의 내용을 file 객체로 전송.(파일 복사)
                filenames.append(newfile).append(",");  //db 테이블에 들어갈 파일명
            } catch (IOException e) {	}
        }
    }
        dto.setPhoto_url(filenames.toString());
		return dao.save(dto);
}


    @Override
    public List<PostDTO> getMyList(String userid) {
        return dao.getmylist(userid);
    }

    @Override
    public int remove(int post_idx) {
        return dao.remove(post_idx);
    }

    @Override
    public int removeCommentAll(int post_idx){
        return dao.removeCommentAll(post_idx);
    }


    @Override
    public PostDTO getPostByIdx(int post_idx) {
        return dao.getPostByIdx(post_idx);
    }


    //좋아요
    @Override
    public Map<String, String> processLikeCount(String data) {
        LikeDTO likes = null;
        Map<String,String> resultMap = new HashMap<>();
        try {
            ObjectMapper objmapper = new ObjectMapper();		//json 과 자바객체 변환하는 매퍼
            likes = objmapper.readValue(data, LikeDTO.class);		//받은 문자열 -> 역직렬화(LikeDTO 타입 객체)
            log.info(">>>> service like 정보 : {}",likes);		//누가 어느 글에 좋아요를 등록/취소 했는지에 대한 값은 likes 객체에 저장
            String alarm = likes.getLikeuser()+"님이 feed " +likes.getPost_idx() + " 번 글에";
            if (likes.isValue()){
                dao.likeTrue(likes);		//좋아요 등록
                alarm += "좋아요를 남겼습니다.";
            } else {
                dao.likeFalse(likes);		//좋아요 취소
                alarm += "좋아요를 취소했습니다.";
            }
            dao.updateLikeCount(likes.getPost_idx());		//좋아요 갯수 update
            int cnt = dao.likes(likes.getPost_idx());		//변경된 갯수 가져오기

            LikeResponseDTO responseDTO =
                    LikeResponseDTO.builder().alarm(alarm).likes_count(cnt).userid(likes.getUserid()).build();
            String result = objmapper.writeValueAsString(responseDTO);		//직렬화(자바객체를 json 문자열로 변환)

            resultMap.put("userid", likes.getUserid());
            resultMap.put("result", result);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return resultMap;
    }

    @Override
    public List<Integer> myLikes(String likeuser) {
        return dao.myLikes(likeuser);
    }



    public List<PostDTO> pagelist(PageRequestDTO pageRequestDTO){

        pageRequestDTO.setSize(5);		//한 페이지에 보이는 글의 갯수 설정
        pageRequestDTO.setDatas();		//start와 end 계산
        List<PostDTO> list = dao.pagelist(pageRequestDTO);		//글 목록

        //페이지 목록 구현은 예정.
        return list;
    }


    @Override
    public int likes(int post_idx) {
        return dao.likes(post_idx);
    }


    public PageResponseDTO listWithSearch(PageRequestDTO pageRequestDTO){
        pageRequestDTO.setSize(99);
        pageRequestDTO.setDatas();
        List<PostDTO> list = dao.pagelist(pageRequestDTO);
        PageResponseDTO responseDTO = PageResponseDTO.of(pageRequestDTO,dao.count(pageRequestDTO),3);
        responseDTO.setList(list);
        return responseDTO;

    }

}


