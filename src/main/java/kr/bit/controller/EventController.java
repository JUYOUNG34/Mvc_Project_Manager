package kr.bit.controller;

import kr.bit.entity.Criteria;
import kr.bit.entity.Criteria;
import kr.bit.entity.Events;
import kr.bit.entity.PageCre;
import kr.bit.entity.PageCre;
import kr.bit.service.EventService;
import kr.bit.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping({"/menu/event", "/controller/menu/event"})
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private LogService logService;
    private String adminId;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/list")
    public String eventList(@AuthenticationPrincipal UserDetails userDetails, Criteria cri , Model model){
        List<Events> eventList = eventService.getEventListWithPaging(cri);
        adminId=userDetails.getUsername();
        // 페이징 정보 설정
        PageCre pageMaker = new PageCre();
        pageMaker.setCriteria(cri);
        pageMaker.setTotalCount(eventService.getTotalCount());

        model.addAttribute("event", eventList);
        model.addAttribute("pageMaker", pageMaker);
        return "menu/event/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("event", new Events());
        return "menu/event/add";
    }

    @PostMapping("/add")
    public String addEvent(@RequestParam(value = "name", required = false) String name,
                           @RequestParam(value = "start_date", required = false) String startDate,
                           @RequestParam(value = "end_date", required = false) String endDate,
                           @RequestParam(value = "file", required = false) MultipartFile file,
                           Model model) {

        if (name == null || name.trim().isEmpty()) {
            model.addAttribute("error", "이벤트 제목을 입력해주세요.");
            return "menu/event/add";
        }

        try {
            Events event = new Events();
            event.setName(name);
            event.setStart_date(startDate);
            event.setEnd_date(endDate);

            // 파일 업로드 처리
            if (file != null && !file.isEmpty()) {
                // 절대 경로 설정
                String uploadDir = "C:/Mvc_Project_Manager/src/main/resources/static/images/events";
                File uploadPath = new File(uploadDir);

                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + fileExtension;

                Path targetPath = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                // 웹 경로로 저장 - 경로 수정
                event.setImage_url("/controller/images/events/" + fileName);
            } else {
                // 기본 이미지 경로 설정
                event.setImage_url("/controller/images/events/default-event.jpg");
            }

            int result = eventService.insertEvent(event);
            System.out.println("이벤트 추가 결과: " + result);

            String logMessage = String.format("%s|%s|%s|%s", LocalDateTime.now().format(formatter),adminId,"이벤트 등록","");
            logService.logAction(logMessage);

            if (result > 0) {
                return "redirect:/menu/event/list";
            } else {
                model.addAttribute("error", "이벤트 등록에 실패했습니다.");
                return "menu/event/add";
            }
        }
        catch (Exception e) {
            log.error("이벤트 추가 중 에러 발생", e);
            model.addAttribute("error", "이벤트 등록에 실패했습니다: " + e.getMessage());
            return "menu/event/add";
        }
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Events event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "menu/event/update";
    }

    @PostMapping("/update/{id}")
    public String updateEvent(@PathVariable int id,
                              @RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "start_date", required = false) String startDate,
                              @RequestParam(value = "end_date", required = false) String endDate,
                              @RequestParam(value = "image", required = false) MultipartFile file,
                              @RequestParam(value = "image_url", required = false) String imageUrl,
                              Model model) {

        log.info("updateEvent 메소드 호출됨! ID: " + id);

        try {
            Events event = new Events();
            event.setId(id);
            event.setName(name);
            event.setStart_date(startDate);
            event.setEnd_date(endDate);

            // 파일 업로드 처리
            if (file != null && !file.isEmpty()) {
                // 절대 경로 설정
                String uploadDir = "C:/Mvc_Project_Manager/src/main/resources/static/images/events";
                File uploadPath = new File(uploadDir);

                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + fileExtension;

                Path targetPath = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                // 웹 경로로 저장
                event.setImage_url("/controller/images/events/" + fileName);
            } else {
                // 기존 이미지 URL 유지
                event.setImage_url(imageUrl);
            }

            eventService.updateEvent(event);
            String logMessage = String.format("%s|%s|%s|%s", LocalDateTime.now().format(formatter),adminId,"이벤트 수정","이벤트 이름 : "+name);
            logService.logAction(logMessage);
            return "redirect:/menu/event/list";
        } catch (Exception e) {
            log.error("이벤트 수정 중 에러 발생", e);
            model.addAttribute("error", "이벤트 수정에 실패했습니다: " + e.getMessage());
            model.addAttribute("event", eventService.getEventById(id));
            return "menu/event/update";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable int id){
        String logMessage = String.format("%s|%s|%s|%s", LocalDateTime.now().format(formatter),adminId,"이벤트 삭제","이벤트 번호 : " + id);
        logService.logAction(logMessage);
        eventService.deleteEvent(id);
        return "redirect:/menu/event/list";
    }
}