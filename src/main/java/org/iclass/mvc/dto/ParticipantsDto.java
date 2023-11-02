package org.iclass.mvc.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantsDto {

    private long ptpIdx;
    private long schIdx;
    private String userid;
}
