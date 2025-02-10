package kr.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu/user")
public class UserController {

    @GetMapping("/userList")
    public String userList(){
        return "menu/user/userList";
    }
    @GetMapping("/userDetail")
    public String userDetail(){
        return "menu/user/userDetail";
    }
    @GetMapping("/chattingList")
    public String chattingList(){
        return "menu/user/chattingList";
    }
    @GetMapping("/chattingDetail")
    public String chattingDetail(){
        return "menu/user/chattingDetail";
    }

}
