package org.iclass.mvc.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupAdDetailDto {

    String userid;
    String username;
    int userage;
    String usergender;
    String userprofile;
    String adanswer;
}
