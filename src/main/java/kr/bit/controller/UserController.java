package kr.bit.controller;



import kr.bit.entity.*;
import kr.bit.service.AdminService;
import kr.bit.service.LogService;
import kr.bit.service.BlacklistService;
import kr.bit.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Controller
@PropertySource({"classpath:application.properties"})
@RequestMapping("/menu/user")
public class UserController {

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Autowired
    private AdminService adminService;

    private String adminId;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @RequestMapping("/userList")
    public String userList(@AuthenticationPrincipal UserDetails userDetails, Criteria criteria, Model model) {
        adminId = userDetails.getUsername();
        // 페이지 번호 확인
        List<User_profiles> userProfiles = userService.getUser_profiles(criteria);

        model.addAttribute("userProfiles", userProfiles);

        int totalCount = userService.getTotalCount(criteria);
        model.addAttribute("totalCount",totalCount);
        PageCre pageCre = new PageCre();
        pageCre.setCriteria(criteria);
        pageCre.setTotalCount(totalCount);
        model.addAttribute("pageCre", pageCre);

        return "menu/user/userList";
    }



    @GetMapping("/userDetail")
    public String userDetail(@RequestParam("user_id") String user_ids, Model model){
        int user_id = Integer.parseInt(user_ids);
        User_profiles userProfile = userService.oneUser_profile(user_id);
        Points point = userService.onePoint(user_id);
        String hobbies = userService.getHobbies(user_id);

        model.addAttribute("userProfile",userProfile);
        model.addAttribute("point",point);
        model.addAttribute("hobbies",hobbies);

        return "menu/user/userDetail";
    }

    @PostMapping("/userDetail/modifyProc")
    public String userDetailModify(@RequestParam("formFile") MultipartFile formFile,
                                   Points points, Model model, RedirectAttributes rttr){

        int user_id = points.getUser_id();
        User_profiles oldProfile = userService.oneUser_profile(user_id);
        if(!formFile.isEmpty()){
            String originalFilename = formFile.getOriginalFilename();

            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileExtension;

            String savePath =  env.getProperty("upload.path")+"user_photos";
//            String newFileUrl = newFileName;
            String oldPhotoUrl = oldProfile.getPhoto_image_url();
            System.out.println(oldPhotoUrl);
            try {
                Path filePath = Paths.get(savePath, newFileName);

                Path directoryPath = filePath.getParent();

                if(!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }
                Files.copy(formFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                if(oldPhotoUrl != null && !oldPhotoUrl.isEmpty()){
                    Files.deleteIfExists(Paths.get(savePath,oldPhotoUrl));
                }
                int result1 = userService.modifyPhoto_image_url(newFileName,user_id);

                int result2 = userService.modifyPoints(points);
                System.out.println("파일저장된곳: "+filePath);
                rttr.addAttribute("msgType","성공");
                rttr.addAttribute("msg","수정 되었습니다.");
            } catch (Exception e) {
                System.out.println("실패" +e);
                rttr.addAttribute("msgType","실패");
                rttr.addAttribute("msg","오류가 발생했습니다.");
            }
        }else {
            userService.modifyPoints(points);
        }
        String logMessage = String.format("%s|%s|%s|%s",
                LocalDateTime.now().format(formatter),adminId,"유저 수정 : "+user_id,
                "포인트 : " + points.getPoints()+" 장작 : "+points.getFirewood() + " 돋보기 : " + points.getReading_glass());
        logService.logAction(logMessage);

        return "redirect:/menu/user/userList";
    }
    @GetMapping("/chat/list")
    public String chatList(@Param("user_id")int user_id,Criteria criteria ,Model model) {
        List<Chat_rooms> chatRooms = userService.getChat_rooms(user_id, criteria);
        System.out.println(chatRooms);
        model.addAttribute("chatRooms",chatRooms);
        model.addAttribute("user_id",user_id);

        int totalCount = userService.getChat_roomsCount(user_id,criteria);

        PageCre pageCre = new PageCre();
        pageCre.setCriteria(criteria);
        pageCre.setTotalCount(totalCount);
        model.addAttribute("pageCre", pageCre);

        return "menu/user/chat/list";
    }


    @GetMapping("/chat/detail")
    public String chatDetail(@RequestParam("room_id") int room_id,@RequestParam("user_id")int user_id ,Model model){
        List<Messages> messages = userService.getMessages(room_id);
        int receiveUserId = userService.receiveUser(user_id,room_id);
        Users user = userService.oneUser(receiveUserId);
        Integer blockId = blacklistService.oneBlockUserID(user_id);

        model.addAttribute("blockId",blockId);
        model.addAttribute("userId",user_id);
        model.addAttribute("messages",messages);
        model.addAttribute("user",user);
        return "menu/user/chat/detail";
    }



    @Autowired
    private BlacklistService blacklistService;

    @GetMapping("/blacklist")
    public String blackList(Criteria criteria, Model model) {
        if (criteria.getCurrent_page() <= 0) {
            criteria.setCurrent_page(1);
        }

        if (criteria.getType() == null || criteria.getType().isEmpty()) {
            criteria.setType("nickname");
        }

        if (criteria.getKeyword() == null) {
            criteria.setKeyword("");
        }

        List<Blacklist> blacklists = blacklistService.getBlacklist(criteria);
        int totalCount = blacklistService.getTotalCount(criteria);

        PageCre pageCre = new PageCre();
        pageCre.setCriteria(criteria);
        pageCre.setTotalCount(totalCount);

        model.addAttribute("blacklists", blacklists);
        model.addAttribute("pageCre", pageCre);
        model.addAttribute("currentPage", criteria.getCurrent_page());
        model.addAttribute("totalPages", (int) Math.ceil((double) totalCount / criteria.getPerPageNum()));
        model.addAttribute("criteria", criteria);

        return "menu/user/blacklist";
    }
    @PostMapping("/chat/blockUser/{user_id}")
    @ResponseBody
    public int blockUser(@PathVariable("user_id") int user_id){
       int blockAdmin= adminService.oneAdmin(adminId).getAdmin_id();
        return blacklistService.blockUser(user_id,blockAdmin);
    }
    @DeleteMapping("/chat/blockCancel/{blocked_user_id}")
    @ResponseBody
    public int blockCancel(@PathVariable("blocked_user_id") int blocked_user_id){
        return blacklistService.blockCancel(blocked_user_id);
    }
    @PostMapping("/blacklist/unblock")
    public String unblockUser(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        boolean success = blacklistService.unblockUser(id);

        if (success) {
            String logMessage = String.format("%s|%s|%s|%s",
                    LocalDateTime.now().format(formatter),adminId,"유저 차단 해제 : "+id, "");
            logService.logAction(logMessage);
            redirectAttributes.addFlashAttribute("message", "사용자 차단이 해제되었습니다.");
        } else {
            redirectAttributes.addFlashAttribute("error", "차단 정보를 찾을 수 없습니다.");
        }

        return "redirect:/menu/user/blacklist";
    }
}