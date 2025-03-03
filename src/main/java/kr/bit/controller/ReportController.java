package kr.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/menu/report")
public class ReportController {
    @GetMapping("/list")
    public String report() {
        return "menu/report/list";
    }
}
