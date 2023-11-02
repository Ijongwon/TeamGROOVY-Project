package org.iclass.mvc.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupDto {
    private String groupidx;
    private String groupmng;
    private String groupname;
    private String groupcate;
    private String groupdetail;
    private int maxp;
    private int nowp;
    private String groupstatus;
    private String adquestion;
    private String groupexp;
    private String grouppic;
    private String grouploc;

    private MultipartFile pic;
}