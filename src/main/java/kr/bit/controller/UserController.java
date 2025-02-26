package kr.bit.controller;



import kr.bit.entity.*;
import kr.bit.service.LogService;
import kr.bit.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/menu/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    boolean isAuthenticated = auth != null &&
            auth.isAuthenticated() &&
            !(auth instanceof AnonymousAuthenticationToken);

    // 사용자 ID 가져오기
    String adminId = isAuthenticated ? auth.getName() : "Anonymous";

    @RequestMapping("/userList")
    public String userList(Criteria criteria, Model model) {
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
    String savePath = "C:/MVCProjectM/Mvc_Project_Manager/src/main/resources/static";

    int user_id = points.getUser_id();
    User_profiles oldProfile = userService.oneUser_profile(user_id);

    if(!formFile.isEmpty()){
        String originalFilename = formFile.getOriginalFilename();

        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileExtension;


        String newFileUrl = "/image/userPhotoImage/"+newFileName;
        String oldPhotoUrl = oldProfile.getPhoto_image_url();

        try {
            Path filePath = Paths.get(savePath, newFileUrl);

            Path directoryPath = filePath.getParent();

            if(!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            Files.copy(formFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            if(oldPhotoUrl != null && !oldPhotoUrl.isEmpty()){
                Files.deleteIfExists(Paths.get(savePath,oldPhotoUrl));
            }
            int result1 = userService.modifyPhoto_image_url(newFileUrl,user_id);

            int result2 = userService.modifyPoints(points);

            rttr.addAttribute("msgType","성공");
            rttr.addAttribute("msg","수정 되었습니다.");
        } catch (Exception e) {
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
        Users user = userService.oneUser(room_id,user_id);
        System.out.println(user.getUser_id());
        model.addAttribute("messages",messages);
        model.addAttribute("user",user);
        return "menu/user/chat/detail";
    }


    @GetMapping("/blacklist")
    public String blackList(){
        return "menu/user/blacklist";
    }


}
