package kr.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu/log")
public class LogController {
    @GetMapping("/list")
    public String logList(){
        return "menu/Log/list";
    }
    @GetMapping("/detail")
    public String logDetail(){
        return "menu/Log/detail";
    }

}
