package kr.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu/manager")
public class AdminController {

    @GetMapping("/managerList")
    public String managerList(){
        return "menu/manager/managerList";
    }
    @GetMapping("/managerModify")
    public String managerModify(){
        return "menu/manager/managerModify";
    }
    @GetMapping("/managerRegister")
    public String managerRegister(){
        return "menu/manager/managerRegister";
    }
}
