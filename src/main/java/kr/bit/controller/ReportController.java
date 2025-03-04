package kr.bit.controller;


import kr.bit.service.LogService;
import kr.bit.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;


@Controller
@RequestMapping("/menu/report")
public class ReportController {
    @Autowired
    private ReportService reportService;


    // 공지사항 목록 조회 (페이징 포함)
    @GetMapping("/list")
    public String ReportsList(Model model) {
        model.addAttribute("reports",reportService.getAllReports() );
        return "menu/report/list";
    }

}
