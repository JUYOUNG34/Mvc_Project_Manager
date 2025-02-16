package kr.bit.controller;

import kr.bit.entity.Admins;
import kr.bit.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
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



    @PostMapping("/auth/login")
    public String auth(@ModelAttribute("admin") Admins admin, BindingResult result) {
        if (result.hasErrors()) {
            return "auth/login";
        }

        Admins loginResult = loginService.adminLogin(admin);
        if (loginResult != null) {
            Admins.setLoginStatus(true);
            return "redirect:/menu/stats";
        }
        return "redirect:/auth/login?fail=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();
        Admins.setLoginStatus(false);
        return "redirect:/auth/login";
    }
}