package org.iclass.mvc.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private int cmt_idx;   //댓글 idx
    private String likeuser;   //댓글 작성자 id
    private int post_idx;		//글 idx
    private String comment_content;     //댓글 내용
    private LocalDateTime created_at;   //작성 날짜
}
