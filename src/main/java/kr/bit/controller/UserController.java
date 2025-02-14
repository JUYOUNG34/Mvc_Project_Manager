package kr.bit.controller;

import kr.bit.entity.*;
import kr.bit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/userList")
    public String userList(Criteria criteria, Model model) {
        // 페이지 번호 확인
        List<User_profiles> userProfiles = userService.getUser_profiles(criteria);

        model.addAttribute("userProfiles", userProfiles);

        int totalCount = userService.getTotalCount(criteria);

        PageCre pageCre = new PageCre();
        pageCre.setCriteria(criteria);
        pageCre.setTotalCount(totalCount);
        model.addAttribute("pageCre", pageCre);

        return "menu/user/userList";
    }



    @GetMapping("/userDetail")
    public String userDetail(@RequestParam("user_id") String user_ids, Model model){
        int user_id = Integer.parseInt(user_ids);
        User_profiles userProfile = userService.oneUser_profile(user_id);
        Points point = userService.onePoint(user_id);
        String hobbies = userService.getHobbies(user_id);

        model.addAttribute("userProfile",userProfile);
        model.addAttribute("point",point);
        model.addAttribute("hobbies",hobbies);

        return "menu/user/userDetail";
    }
    @GetMapping("/chat/detail")
    public String chatDetail(@RequestParam("user_id") String user_ids,Model model){
        int user_id = Integer.parseInt(user_ids);
        List<Chat_rooms> chat_rooms = userService.getChat_rooms(user_id);
        model.addAttribute("chat_rooms",chat_rooms);
        return "menu/user/chat/detail";
    }
    @GetMapping("/chat/list")
    public String chatList(){
        return "menu/user/chat/list";
    }

}
