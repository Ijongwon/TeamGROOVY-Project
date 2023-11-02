package org.iclass.mvc.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    //좋아요 결과로 돌려줄 메시지와 갯수.
    private String userid;  //글 작성자
    private String alarm;   //메시지
    private int comments_count;     //현재 좋아요 갯수
}
