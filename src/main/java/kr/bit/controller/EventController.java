package kr.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu/event")
public class EventController {
    @GetMapping("/list")
    public String logList(){
        return "menu/event/list";
    }

}
