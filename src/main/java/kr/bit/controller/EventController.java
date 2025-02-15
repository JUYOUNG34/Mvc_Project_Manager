package kr.bit.controller;

import kr.bit.entity.Events;
import kr.bit.service.EventService;
import lombok.extern.slf4j.Slf4j;
;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/menu/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/list")
    public String eventList(Model model){
        model.addAttribute("event", eventService.getAllEvents());
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



        System.out.println("========== 이벤트 추가 시작 ==========");
        System.out.println("이벤트 이름: " + name);
        System.out.println("시작일: " + startDate);
        System.out.println("종료일: " + endDate);

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

                String projectPath = System.getProperty("user.dir");
                String uploadDir = projectPath + "/src/main/resources/static/images/events";
                File uploadPath = new File(uploadDir);

                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                String originalFilename = file.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + fileExtension;

                Path targetPath = Paths.get(uploadDir, fileName);
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                // 웹 경로 수정
                event.setImage_url("/src/main/resources/static/images/events/" + fileName);
            } else {

                event.setImage_url("/src/main/resources/static/images/events/default-event.jpg");
            }

            int result = eventService.insertEvent(event);
            System.out.println("이벤트 추가 결과: " + result);

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
    public String updateEvent(@PathVariable int id, @ModelAttribute Events event){
        event.setId(id);
        eventService.updateEvent(event);
        return "redirect:/menu/event/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable int id){
        eventService.deleteEvent(id);
        return "redirect:/menu/event/list";
    }

}