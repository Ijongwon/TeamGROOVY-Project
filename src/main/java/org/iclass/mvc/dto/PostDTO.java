package org.iclass.mvc.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private long r;
    private int post_idx;   //글 idx
    private String userid;     //작성자 id
    private String photo_url;   //사진 url
    private String content;     //글 내용
    private Timestamp created_at;   //작성 날짜
    private int comments_count;     //댓글 갯수
    private int likes_count;        //좋아요 갯수
    private String hashtags;        //해시 태그

    //테이블 컬럼에는 없고, 파일업로드에 순수하게 DTO 용도로 사용합니다.
    private List<MultipartFile> pics;
}


