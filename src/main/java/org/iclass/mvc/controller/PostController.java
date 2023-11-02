package org.iclass.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.iclass.mvc.dto.*;
import org.iclass.mvc.service.CommentService;
import org.iclass.mvc.service.PostService;
import org.iclass.mvc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class PostController {

    private final PostService service;
    private final UserService uservice;



    @GetMapping("/feed")
    public void pagelist(
            PageRequestDTO pageRequestDTO, Model model, HttpSession session) {

        String likeuser = (String)session.getAttribute("userid");

        List<PostDTO> list = service.getList();



        model.addAttribute("list", list);


        if (likeuser !=null) {
            model.addAttribute("myLikes", service.myLikes(likeuser));

        }else {
            model.addAttribute("myLikes", List.of(0));

        }
        //로그인 사용자의 좋아요 글목록을 저장하기
        log.info("*****************************");


        log.info(">>>>>> pageRequestDTO: {}", pageRequestDTO);

        PageResponseDTO responseDTO = service.listWithSearch(pageRequestDTO);

        responseDTO.getList().forEach(i ->{
            log.info(">>>>> 글 : {}",i);
        });


        model.addAttribute("paging", responseDTO);
        model.addAttribute("today", LocalDate.now());


    }
    @GetMapping("/userFeed")
    public void getMyList(@RequestParam("userid") String userid, Model model) {
        List<PostDTO> list = service.getMyList(userid);
        UserDto dto = uservice.selectByid(userid);
        model.addAttribute("list",list);
        model.addAttribute("udto",dto);

    }


    @GetMapping("/post")
    public void save(){
    }

    @PostMapping("/post")
    public String save(PostDTO vo){
        int count = service.save(vo);
        log.info("------------RequestDTO: {}",vo);
        return "redirect:/board/feed";

    }

    @PostMapping("/delete")
    public String remove(int post_idx ){
        service.removeCommentAll(post_idx);
        service.remove(post_idx);

        return "redirect:/board/feed";
    }

    @GetMapping("/feedDetail")
    public void getPostDetails(int post_idx,Model model, @SessionAttribute("userid") String likeuser) {
        PostDTO vo = service.getPostByIdx(post_idx);
        model.addAttribute("vo",vo);
        model.addAttribute("myLikes", service.myLikes(likeuser));

    }






}