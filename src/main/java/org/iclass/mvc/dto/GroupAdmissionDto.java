package org.iclass.mvc.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupAdmissionDto {

    String groupidx;
    String userid;
    String adanswer;
    String adstatus;
    String userprofile;


}
