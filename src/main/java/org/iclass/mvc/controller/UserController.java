package org.iclass.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.iclass.mvc.dto.ScheduleDto;
import org.iclass.mvc.dto.UserDto;
import org.iclass.mvc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Log4j2


public class UserController {

    private final UserService service;

    @GetMapping("/userjoin")
    public void joinview(){

    }
    @GetMapping("/index1")
    public void index(){

    }

    @PostMapping("/userjoin")
    public String userjoin(UserDto dto , RedirectAttributes reAttr){
        service.insert(dto);

        reAttr.addFlashAttribute("message","회원가입이 완료되었습니다.");
        return "redirect:/user/login";
    }


    @GetMapping("/login")
    public void login() {

    }

    @PostMapping("/login")
    public String loginProc(@RequestParam Map<String,String> param , Model model,
                            RedirectAttributes reattr, HttpSession session,String userid,String username){
        String url="/"; //계정 일치하면 index이동
        String pic = service.selectPic(userid);
        UserDto dto = service.login(param);
        log.info(">>>>>>>>> file {}:",pic); log.info(">>>>>>>>> file {}:",pic);
        if(dto==null) {
            reattr.addFlashAttribute("incorrect", "y");
            reattr.addFlashAttribute("message","계정 정보가 틀립니다.");
            url = "login";    //계정정보틀리면 로그인으로 이동
        }else{
            model.addAttribute("user",dto);
            session.setAttribute("userid",userid);
            session.setAttribute("username",username);
            session.setAttribute("pic",pic);


        }

        return "redirect:"+url;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session , RedirectAttributes reAttr) {
        session.invalidate();
        reAttr.addFlashAttribute("message","로그아웃이 완료되었습니다.");
        return "redirect:/";
    }


    @GetMapping("/mypage")
    public void mypage(HttpSession session,Model model){
        String id = (String) session.getAttribute("userid");
        UserDto dto = service.selectByid(id);
        log.info(">>>>> {} ", dto);
        model.addAttribute("vo",dto);
    }

    @GetMapping("/calendar")
    public void calendar(@RequestParam(required = false) String groupidx,
//                         @RequestParam(required = false) LocalDate schdates,
                         Model model){

        if(groupidx==null) groupidx = "2";

        List<ScheduleDto> dto = service.selectByGroupIdx(groupidx);

        log.info(">>>>>>>>>>>>>>>>>groupIdx {}", groupidx);
        log.info(">>>>>>>>>>>>>>>>>dto {}", dto);

        /*=================================================*/
//        if(schdates==null) schdates = LocalDate.parse("2023-10-20");
//        List<ScheduleDto> list = service.selectSchByDate(schdates);
//        model.addAttribute("list", list);

        model.addAttribute("groupidx", groupidx);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/calendar")
    public String calendar(ScheduleDto dto, Model model){

        log.info(">>>>>>>>>>>>>>>>>ScheduleDto {}", dto);

        service.schinsert(dto);

        return "redirect:/user/calendar?groupidx="+dto.getGroupidx();
    }

    @GetMapping("/userupdate")
    public void updateview(Model model,HttpSession session){
        String userid = (String) session.getAttribute("userid");
        model.addAttribute("vo",service.selectByid(userid));

    }
    @PostMapping("/userupdate")
    public String update(UserDto dto, RedirectAttributes redirectAttributes,HttpSession session){
        service.update(dto);
        redirectAttributes.addFlashAttribute("message","회원정보 수정이 완료되었습니다.");
        String userid = dto.getUserid();
        String userpic = service.selectByid(userid).getUserprofile();
        session.setAttribute("pic",userpic);

        return "redirect:/user/mypage";
    }
}




