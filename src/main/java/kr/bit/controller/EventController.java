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
    public String addEvent(@ModelAttribute Events event,
                           @RequestParam("file") MultipartFile file) {

        // 이미지 처리
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                String uploadDir = "src/main/resources/static/images/events/";
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                event.setImage_url("/images/events/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 데이터 확인 로그
        log.info("Event data: {}", event);

        eventService.insertEvent(event);
        return "redirect:/menu/event/list";
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