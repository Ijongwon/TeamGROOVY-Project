package org.iclass.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.iclass.mvc.dto.GroupAdmissionDto;
import org.iclass.mvc.dto.GroupDto;
import org.iclass.mvc.dto.GroupMemberDto;
import org.iclass.mvc.dto.UserDto;
import org.iclass.mvc.service.ChatService;
import org.iclass.mvc.service.GroupService;
import org.iclass.mvc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/group")
@RequiredArgsConstructor
@Log4j2
public class GroupController {

    private final GroupService service;
    private final UserService uservice;
   private final ChatService chatservice;

    @GetMapping("/groupinsert")
    public void insertview(){

    }

    @GetMapping("/groupupdate")
    public void updateview(String groupidx, Model model){
        model.addAttribute("dto",service.selectByidx(groupidx));
    }

    @PostMapping("/groupupdate")
    public String update(GroupDto dto) {
        service.groupupdate(dto);
        return "redirect:/group/groupdetail?groupidx="+dto.getGroupidx();
    }

    @GetMapping("/grouplist")
    public void grouplist(){

    }

    @PostMapping("/groupinsert")
    public String groupinsert(GroupDto dto,HttpSession session) {
        String id = (String)session.getAttribute("userid");
        dto.setGroupmng(id);
        service.insert(dto);
        GroupMemberDto gdto = GroupMemberDto.builder()
                .groupidx(dto.getGroupidx())
                .userid(id)
                .userprofile(uservice.selectByid(id).getUserprofile())
                .build();
        log.info(">>>>>>>>>>> idx : {}",dto.getGroupidx());

        service.memberInsert(gdto);
        return "redirect:/";
    }



    @GetMapping("/groupdetail")
    public void detail( String groupidx, Model model,HttpSession session) {
        String id = (String)session.getAttribute("userid");
        if (id == null) {
            id = "null";
        }
        GroupDto dto = service.selectByidx(groupidx);
        UserDto udto = uservice.selectByid(dto.getGroupmng());
        int grstatus = service.isInGroup(groupidx,id);
        int ad = service.Admission(groupidx,id);


        List<GroupMemberDto> memberList = service.memberList(groupidx);
        model.addAttribute("userid",id);
        model.addAttribute("dto",dto);
        model.addAttribute("udto",udto);
        model.addAttribute("grstatus",grstatus);
        model.addAttribute("adstatus",ad);
        model.addAttribute("member",memberList);
        model.addAttribute("groupidx",groupidx);
        model.addAttribute("list",chatservice.roomlist(groupidx));


    }

    @PostMapping("/memberInsert")
    public String memberinsert(String groupidx,HttpSession session){
        String id = (String)session.getAttribute("userid");
        GroupMemberDto gdto = GroupMemberDto.builder()
                .groupidx(groupidx)
                .userid(id)
                .userprofile(uservice.selectByid(id).getUserprofile())
                .build();

        service.memberInsert(gdto);
        service.updatenowp(groupidx);
        return "redirect:/";
    }

    @PostMapping("/insertAdmission")
    public String insertAdmission(String groupidx, String adanswer,HttpSession session) {
        String id = (String)session.getAttribute("userid");
        GroupAdmissionDto gdto = GroupAdmissionDto.builder()
                        .groupidx(groupidx)
                        .userid(id)
                        .userprofile(uservice.selectByid(id).getUserprofile())
                        .adanswer(adanswer)
                .build();
        service.insertAdmission(gdto);
        return "redirect:/";
    }

    @GetMapping("/groupmember")
    public void groupmember(String groupidx, Model model) {

        model.addAttribute("dto",groupidx);
        model.addAttribute("group",service.selectByidx(groupidx));
    }

}
