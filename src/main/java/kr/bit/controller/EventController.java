package kr.bit.controller;

import kr.bit.entity.Events;
import kr.bit.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    private static final String UPLOAD_DIR = "src/main/resources/static/images/events/";
    private static final String IMAGE_URL_PREFIX = "/images/events/";

    @Autowired
    private EventService eventService;

    @GetMapping("/list")
    public String eventList(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "menu/event/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("event", new Events());
        return "menu/event/add";
    }

    @PostMapping("/add")
    public String addEvent(@ModelAttribute Events event,
                           @RequestParam("file") MultipartFile file,
                           RedirectAttributes redirectAttributes) {
        try {
            if (file != null && !file.isEmpty()) {
                String imageUrl = saveImage(file);
                event.setImage_url(imageUrl);
            }

            int result = eventService.insertEvent(event);
            if (result > 0) {
                redirectAttributes.addFlashAttribute("message", "이벤트가 성공적으로 추가되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("error", "이벤트 추가에 실패했습니다.");
            }
        } catch (IOException e) {
            log.error("이벤트 추가 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "이미지 업로드 중 오류가 발생했습니다.");
        }

        return "redirect:/menu/event/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Events event = eventService.getEventById(id);
        if (event == null) {
            return "redirect:/menu/event/list";
        }
        model.addAttribute("event", event);
        return "menu/event/update";
    }

    @PostMapping("/update/{id}")
    public String updateEvent(@PathVariable int id,
                              @ModelAttribute Events event,
                              @RequestParam(value = "file", required = false) MultipartFile file,
                              RedirectAttributes redirectAttributes) {
        try {
            Events existingEvent = eventService.getEventById(id);
            if (existingEvent == null) {
                redirectAttributes.addFlashAttribute("error", "이벤트를 찾을 수 없습니다.");
                return "redirect:/menu/event/list";
            }

            // 새 이미지가 업로드된 경우에만 이미지 처리
            if (file != null && !file.isEmpty()) {
                deleteExistingImage(existingEvent.getImage_url());
                String imageUrl = saveImage(file);
                event.setImage_url(imageUrl);
            } else {
                // 새 이미지가 없으면 기존 이미지 URL 유지
                event.setImage_url(existingEvent.getImage_url());
            }

            event.setId(id);
            int result = eventService.updateEvent(event);

            if (result > 0) {
                redirectAttributes.addFlashAttribute("message", "이벤트가 성공적으로 수정되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("error", "이벤트 수정에 실패했습니다.");
            }
        } catch (IOException e) {
            log.error("이벤트 수정 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "이미지 업로드 중 오류가 발생했습니다.");
        }

        return "redirect:/menu/event/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteEvent(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            Events event = eventService.getEventById(id);
            if (event != null && event.getImage_url() != null) {
                deleteExistingImage(event.getImage_url());
            }

            int result = eventService.deleteEvent(id);
            if (result > 0) {
                redirectAttributes.addFlashAttribute("message", "이벤트가 성공적으로 삭제되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("error", "이벤트 삭제에 실패했습니다.");
            }
        } catch (IOException e) {
            log.error("이벤트 삭제 중 오류 발생", e);
            redirectAttributes.addFlashAttribute("error", "이벤트 삭제 중 오류가 발생했습니다.");
        }

        return "redirect:/menu/event/list";
    }

    private String saveImage(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return IMAGE_URL_PREFIX + fileName;
    }

    private void deleteExistingImage(String imageUrl) throws IOException {
        if (imageUrl != null && imageUrl.startsWith(IMAGE_URL_PREFIX)) {
            String fileName = imageUrl.substring(IMAGE_URL_PREFIX.length());
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.deleteIfExists(filePath);
        }
    }
}