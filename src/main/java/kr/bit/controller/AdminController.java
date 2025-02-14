package kr.bit.controller;

import kr.bit.entity.Admins;
import kr.bit.entity.Criteria;
import kr.bit.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/menu/manager")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/list")
    public String managerList(Criteria criteria, Model model){
        List<Admins> admins = adminService.getAdmins(criteria);
        model.addAttribute("admins",admins);

        return "menu/manager/list";
    }
    @GetMapping("/register")
    public String managerDetail(){
        return "menu/manager/register";
    }
    @GetMapping("/modify")
    public String managerBlock(){
        return "menu/manager/modify";
    }
}
