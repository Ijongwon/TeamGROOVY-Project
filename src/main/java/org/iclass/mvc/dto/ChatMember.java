package org.iclass.mvc.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMember {
    private String userid;
    private int roomnum;
    private boolean readstatus;
    private LocalDate enterdate;

}
