package kr.bit.controller;

import com.mysql.cj.protocol.x.Notice;
import kr.bit.entity.Boards;
import kr.bit.service.NoticeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/menu/board/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;


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

