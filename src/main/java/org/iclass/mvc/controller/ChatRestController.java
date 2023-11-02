package org.iclass.mvc.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.mvc.dto.ChatRoom;
import org.iclass.mvc.dto.GroupDto;
import org.iclass.mvc.service.ChatService;
import org.iclass.mvc.service.GroupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRestController {

    private final ChatService service;
    private final GroupService groupservice;

    @GetMapping("/mychatList/{userid}")
    public List<GroupDto> mychatList(@PathVariable String userid){
        List<GroupDto> list = groupservice.mychatgroupname(userid);
        return list;
    }

    @GetMapping("/groupchatList/{groupidx}")
    public List<ChatRoom> selectByid(@PathVariable String groupidx, HttpSession session){
        String userid = (String)session.getAttribute("userid");
        List<ChatRoom> list = service.selectByid(userid,groupidx);
        return list;
    }


}
