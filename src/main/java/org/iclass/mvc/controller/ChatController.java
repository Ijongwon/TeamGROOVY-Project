package org.iclass.mvc.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.mvc.dto.ChatMessage;
import org.iclass.mvc.dto.ChatRoom;
import org.iclass.mvc.service.ChatMemberService;
import org.iclass.mvc.service.ChatMessageService;
import org.iclass.mvc.service.ChatService;
import org.iclass.mvc.service.GroupService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    public final SimpMessagingTemplate template;
    public final ChatService service;
    public final ChatMessageService chatservice;
    public final ChatMemberService chatMemberservice;
    public final GroupService groupservice;

    @MessageMapping("/chatroom/enter")
    public void enter(ChatMessage message) {
        service.memberupcnt(message.getCm_roomnum());
        message.setContent(message.getCm_sender()+"님이 입장하셨습니다.");
        template.convertAndSend("/sub/chatroom/"+message.getCm_roomnum(), message);
        chatMemberservice.chatmemberjoin(message.getCm_sender(), message.getCm_roomnum());
    }

    @MessageMapping("/chatroom/{roomnum}")
    public void message(ChatMessage message) {
        template.convertAndSend("/sub/chatroom/" + message.getCm_roomnum(), message);
        chatservice.chatinsert(message);

    }

    @MessageMapping("/chatroom/leave")
    public void leave(ChatMessage message){
        service.memberdowncnt(message.getCm_roomnum());
        message.setContent(message.getCm_sender()+"님이 퇴장하셨습니다.");
        template.convertAndSend("/sub/chatroom/"+message.getCm_roomnum(),message);
    }



    @GetMapping("/chatForm")
    public void chatformView(String groupidx,Model model){

        model.addAttribute("groupidx",groupidx);
    }

    @PostMapping("/chatForm")
    public String chatform(ChatRoom dto){
        service.makechat(dto);
        return "redirect:/group/groupdetail?groupidx="+dto.getGroupidx();
    }

    @GetMapping("/groupchatList")
    public void chatListView(String groupidx, Model model){

        model.addAttribute("list",service.roomlist(groupidx));
        model.addAttribute("groupidx",groupidx);
        //model.addAttribute("chatmember",chatservice.chatmember(roomnum));
    }

    @GetMapping("/chatDetails")
    public void chatDetailsView(int roomnum,Model model){
        model.addAttribute("roomnum",service.roomNum(roomnum));
        model.addAttribute("list",chatservice.chatlist(roomnum));
    }

    @GetMapping("/mychatList")
    public void mychatListView(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        String userid = (String) session.getAttribute("userid");
            model.addAttribute("groups",groupservice.mychatgroupname(userid));
            model.addAttribute("admissons",groupservice.myadmission(userid));
            model.addAttribute("hearts",groupservice.myHeart(userid));

    }



}
