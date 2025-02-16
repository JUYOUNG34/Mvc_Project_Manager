package kr.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu/note")
public class NoteController {
    @GetMapping("/list")
    public String note() {
        return "menu/note/list";
    }
}
