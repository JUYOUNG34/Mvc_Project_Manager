package kr.bit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        return "redirect:/auth/login";
    }

    @GetMapping("/controller")
    public String oldContextPath() {
        return "redirect:/";
    }

    @GetMapping("/controller/**")
    public String handleLegacyUrls(HttpServletRequest request) {
        String path = request.getRequestURI();
        // 중복된 /controller 제거
        path = path.replaceFirst("/controller/controller", "/controller");

        return "redirect:" + path.substring("/controller".length());
    }

    @GetMapping("/controller/logout")
    public String handleLegacyLogout() {
        return "redirect:/auth/logout";
    }
}