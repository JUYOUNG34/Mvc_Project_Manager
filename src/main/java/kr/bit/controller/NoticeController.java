package kr.bit.controller;


import kr.bit.entity.Boards;
import kr.bit.service.LogService;
import kr.bit.service.NoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;


@Controller
@RequestMapping("/menu/board/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private LogService logService;
    private String adminId;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 공지사항 목록 조회 (페이징 포함)
    @GetMapping("/list")
    public String BoardsList(Model model) {
        model.addAttribute("notices", noticeService.getAllNotices());
        return "menu/board/notice/list";
    }

    @GetMapping("/notice/detail/{id}")
    public String noticeDetail(@PathVariable("id")int id, Model model){
        Boards getDetailNotice = noticeService.getDetailNotice(id);
        model.addAttribute("notices",getDetailNotice);
        return "menu/board/notice/detail/{id}";
    }
}

