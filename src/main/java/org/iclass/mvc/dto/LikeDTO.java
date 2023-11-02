package org.iclass.mvc.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {
    private String userid;	//글 작성자
    private String likeuser;	//좋아요 누른 사용자
    private int post_idx;		//글 idx
    private String status;	//글 확인 여부
    private boolean value;	//좋아요 체크박스 상태 : true, false (테이블 컬럼과 상관 없다.)

}
