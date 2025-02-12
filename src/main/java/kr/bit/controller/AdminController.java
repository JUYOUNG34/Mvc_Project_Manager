package kr.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu/manager")
public class AdminController {

    @GetMapping("/list")
    public String managerList(){
        return "menu/manager/list";
    }
    @GetMapping("/detail")
    public String managerDetail(){
        return "menu/manager/detail";
    }
    @GetMapping("/block")
    public String managerBlock(){
        return "menu/manager/blcok";
    }
}
