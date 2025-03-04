package kr.bit.controller;

import kr.bit.entity.note;
import kr.bit.service.LogService;
import kr.bit.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/menu/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("/list")
    public String note(Model model, HttpSession session) {
        // 세션에서 현재 로그인한 사용자의 ID를 가져옴
        Integer userId = (Integer) session.getAttribute("userId");

        if(userId != null) {
            List<note> receivedNotes = noteService.getReceivedNotes(userId);
            List<note> sentNotes = noteService.getSentNotes(userId);

            model.addAttribute("receivedNotes", receivedNotes);
            model.addAttribute("sentNotes", sentNotes);
        }

        return "menu/note/list";
    }
}