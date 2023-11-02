package org.iclass.mvc.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupMemberDto {
        String groupidx;
        String userid;
        LocalDate groupjoindate;
        String userprofile;

}
