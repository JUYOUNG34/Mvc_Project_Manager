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
    @GetMapping("/chat/detail")
    public String chatdetail(){
        return "menu/user/chat/detail";
    }
    @GetMapping("/chat/List")
    public String chatList(){
        return "menu/user/chat/List";
    }

}
