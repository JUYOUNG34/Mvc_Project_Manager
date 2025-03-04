package kr.bit.controller;

import kr.bit.entity.Admins;
import kr.bit.entity.Criteria;
import kr.bit.entity.PageCre;
import kr.bit.security.AdminUserDetailsService;
import kr.bit.service.AdminService;
import kr.bit.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/menu/manager")
public class AdminController {


    @Autowired
    private AdminService adminService;
    @Autowired
    private LogService logService;

    private String adminId;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @GetMapping("/list")
    public String managerList(@AuthenticationPrincipal UserDetails userDetails, Criteria criteria, Model model){

        // UserDetails를 Admins 객체로 변환 (로그인 시에 Admins의 ID와 권한을 저장했다고 가정)
        adminId = userDetails.getUsername();

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
    public String managerRegister(Model model){
        model.addAttribute("admins",new Admins());
        return "menu/manager/register";
    }
    @CrossOrigin(origins = "*")
    @GetMapping("/idDuplicate/{id}")
    public @ResponseBody String idDuplicate(@PathVariable("id")String id){
        String is="";
        if(adminService.oneAdmin(id) == null){
            is="true";
        }else {
            is="false";
        }
        return is;
    }

    @PostMapping("/registerProc")
    public String registerPrc(@ModelAttribute("admins") Admins admins, RedirectAttributes rttr){
        boolean result = adminService.registerAdmin(admins);
        if(result){
            rttr.addFlashAttribute("msgType","성공");
            rttr.addFlashAttribute("msg","관리자가 등록 되었습니다.");
        }else {
            rttr.addFlashAttribute("msgType","실패");
            rttr.addFlashAttribute("msg","관리자가 실패 되었습니다.");
        }
        String logMessage = String.format("%s|%s|%s|%s",
                LocalDateTime.now().format(formatter),adminId,"관리자 등록 : "+admins.getId(),"");
        logService.logAction(logMessage);
        return "redirect:/menu/manager/register";
    }
    @GetMapping("/modify")
    public String managerModify(@Param("id") String id, Model model){
        Admins admin= adminService.oneAdmin(id);
        model.addAttribute("admin",admin);
        return "menu/manager/modify";
    }
    @CrossOrigin(origins = "*")
    @PutMapping("/modifyProc")
    public @ResponseBody String modifyProc(@RequestBody Admins admin){
        String result = "";
        if(adminService.updateAdmin(admin)){
            result="true";
        }else {
            result="false";
        }
        String logMessage = String.format("%s|%s|%s|%s",
                LocalDateTime.now().format(formatter),adminId,"관리자 수정 : " + admin.getId(),
                "비밀번호 : " + admin.getPass() + " 권한 : "+admin.getAdmin_level() );
        logService.logAction(logMessage);
        return result;
    }
    @CrossOrigin(origins = "*")
    @DeleteMapping("/deleteProc/{id}")
    public @ResponseBody String deleteProc(@PathVariable("id")String id){
        String result = "";
        if(adminService.deleteAdmin(id)){
            result="true";
        }else {
            result="false";
        }
        String logMessage = String.format("%s|%s|%s|%s",
                LocalDateTime.now().format(formatter),adminId,"관리자 삭제 : "+id,"");
        logService.logAction(logMessage);
        return result;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/myPassCheck/{id}")
    @ResponseBody
    private String myPassCheck(@PathVariable("id") String id){
        return adminService.oneAdmin(id).getPass();
    }

}