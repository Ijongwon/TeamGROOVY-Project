package org.iclass.mvc.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ChatRoom {
    private int roomnum;
    private String roomname;
    private int membercnt;
    private LocalDate roomdate;
    private String groupidx;
}
