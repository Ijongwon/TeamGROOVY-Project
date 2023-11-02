package org.iclass.mvc.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupMemberDetailDto {
    String groupidx;
    String userid;
    String username;
    String userintro;
    LocalDate groupjoindate;


}
