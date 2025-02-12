package kr.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/menu")
public class ReportController {
    @GetMapping("/stats")
    public String stats() {
        return "menu/stats";
    }
}
