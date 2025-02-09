package kr.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {


    @GetMapping
    public String root() {
        return "redirect:/auth/login";
    }


    @GetMapping("/auth/login")
    public String loginForm() {
        return "auth/login";
    }


}