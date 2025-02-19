package kr.bit.controller;

import kr.bit.entity.Admins;
import kr.bit.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public String root() {
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/login")
    public String loginForm(Model model) {
        model.addAttribute("admin", new Admins());
        return "auth/login";
    }



    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {

        if (error != null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        if (logout != null) {
            model.addAttribute("message", "로그아웃되었습니다.");
        }

        return "auth/login";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "auth/login";
    }


}