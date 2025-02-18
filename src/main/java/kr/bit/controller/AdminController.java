package kr.bit.controller;

import kr.bit.entity.Admins;
import kr.bit.entity.Criteria;
import kr.bit.entity.PageCre;
import kr.bit.service.AdminService;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

        int totalCount = adminService.getTotalCount(criteria);

        PageCre pageCre = new PageCre();
        pageCre.setCriteria(criteria);
        pageCre.setTotalCount(totalCount);
        model.addAttribute("pageCre", pageCre);

        return "menu/manager/list";
    }
    @GetMapping("/register")
    public String managerDetail(Model model){
        model.addAttribute("admins",new Admins());
        return "menu/manager/register";
    }
    @GetMapping("/idDuplicate/{id}")
    public @ResponseBody boolean idDuplicate(@PathVariable("id")String id){
        return adminService.oneAdmin(id) == null;
    }
    @PostMapping("/register_proc")
    public String registerPrc(@ModelAttribute("admins") Admins admins, RedirectAttributes rttr){
        boolean result = adminService.registerAdmin(admins);
        if(result){
            rttr.addFlashAttribute("msgType","성공");
            rttr.addFlashAttribute("msg","관리자가 등록 되었습니다.");
        }else {
            rttr.addFlashAttribute("msgType","실패");
            rttr.addFlashAttribute("msg","관리자가 실패 되었습니다.");
        }
        return "redirect:/menu/manager/register";
    }
    @GetMapping("/modify")
    public String managerBlock(@Param("id") String id, Model model){
        Admins admin= adminService.oneAdmin(id);
        model.addAttribute("admin",admin);
        return "menu/manager/modify";
    }
    @PutMapping("/modifyProc")
    public @ResponseBody boolean modifyProc(@RequestBody Admins admin){
        return adminService.updateAdmin(admin);
    }
    @DeleteMapping("/deleteProc/{id}")
    public @ResponseBody boolean deleteProc(@PathVariable("id")String id){
        return adminService.deleteAdmin(id);
    }
}
