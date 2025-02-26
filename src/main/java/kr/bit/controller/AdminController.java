package kr.bit.controller;

import kr.bit.entity.Admins;
import kr.bit.entity.Criteria;
import kr.bit.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
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
        model.addAttribute("admins", admins);
        model.addAttribute("criteria", criteria);
        return "menu/manager/list";
    }

    @GetMapping("/register")
    public String managerRegisterForm(Model model){
        model.addAttribute("admins", new Admins());
        return "menu/manager/register";
    }

    @PostMapping("/register_proc")
    public String registerAdmin(@ModelAttribute Admins admin, RedirectAttributes redirectAttributes) {
        // admin_id는 자동 생성되므로 설정할 필요 없음
        // employee_number 임의 생성 (실제로는 비즈니스 로직에 맞게 생성해야 함)
        admin.setEmployee_number("EMP" + System.currentTimeMillis());

        boolean success = adminService.registerAdmin(admin);

        if (success) {
            redirectAttributes.addFlashAttribute("message", "관리자가 성공적으로 등록되었습니다.");
            return "redirect:/menu/manager/list";
        } else {
            redirectAttributes.addFlashAttribute("error", "관리자 등록에 실패했습니다.");
            return "redirect:/menu/manager/register";
        }
    }

    @GetMapping("/modify/{adminId}")
    public String managerModifyForm(@PathVariable int adminId, Model model){
        Admins admin = adminService.getAdminById(adminId);
        model.addAttribute("admin", admin);
        return "menu/manager/modify";
    }

    @PostMapping("/modify_proc")
    public String modifyAdmin(@ModelAttribute Admins admin, RedirectAttributes redirectAttributes) {
        boolean success = adminService.updateAdmin(admin);

        if (success) {
            redirectAttributes.addFlashAttribute("message", "관리자 정보가 성공적으로 수정되었습니다.");
            return "redirect:/menu/manager/list";
        } else {
            redirectAttributes.addFlashAttribute("error", "관리자 정보 수정에 실패했습니다.");
            return "redirect:/menu/manager/modify/" + admin.getAdmin_id();
        }
    }

    @PostMapping("/delete/{adminId}")
    public String deleteAdmin(@PathVariable int adminId, RedirectAttributes redirectAttributes) {
        boolean success = adminService.deleteAdmin(adminId);

        if (success) {
            redirectAttributes.addFlashAttribute("message", "관리자가 성공적으로 삭제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("error", "관리자 삭제에 실패했습니다.");
        }

        return "redirect:/menu/manager/list";
    }

    @GetMapping("/check-id")
    @ResponseBody
    public boolean checkIdDuplicate(@RequestParam String id) {
        return adminService.isIdDuplicate(id);
    }
}