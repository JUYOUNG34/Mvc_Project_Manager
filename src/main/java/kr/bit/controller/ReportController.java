package kr.bit.controller;

import kr.bit.entity.Criteria;
import kr.bit.entity.Messages;
import kr.bit.entity.Reports;
import kr.bit.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/list")
    public String report(@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                         @RequestParam(value = "searchType", required = false) String searchType, Model model) {
        List<Reports> reports;

        if (searchKeyword != null || searchType != null) {
            reports = reportService.getReports(searchType, searchKeyword);
        } else {
            reports = reportService.getReports(null,null);
        }
        model.addAttribute("reports", reports);

        return "menu/report/list";
    }

    @GetMapping("/detail/{id}")
    public String getDetailReports(@PathVariable int id, Model model) {
        Reports report = reportService.getDetailReports(id);
        model.addAttribute("report", report);
        System.out.println(report);
        //current_id가 messages 테이블의 room_id이기 때문에 해당 id를 이용해서 해당 방의 메시지를 가져오는데 사용하면 됨
        return "menu/report/detail";
    }

    @GetMapping("/updateList")
    public String setReportStatus(@RequestParam(value = "id", required = false) Integer id,
                                  @RequestParam(value = "changeStatus", required = false) String changeStatus,
                                  Model model) {
//        System.out.println(id + "  " + changeStatus);
        reportService.updateReportStatus(id, changeStatus);

        return "menu/report/list";
    }

    // 신고 내역 삭제
    @PostMapping("/delete/{id}")
    public String deleteReport(@PathVariable("id") int id) {
        reportService.deleteReport(id);  // 신고 내역 삭제 서비스 호출
        return "redirect:/menu/report/list";  // 삭제 후 목록 페이지로 리다이렉트
    }

    @GetMapping("/chat/{id}")
    public String getChatReports(@PathVariable int id, Model model) {
        //신고테이블에서 가져온 messages 테이블의 room_id 값
        List<Reports> reports = reportService.getCurrentId(id);
        model.addAttribute("report", reports.get(0).getReported_id());

        int room_id = reports.get(0).getCurrent_id();
        List<Messages> messages = reportService.getMessages(room_id);
        model.addAttribute("messages", messages);
        return "menu/report/chat";
    }
}


