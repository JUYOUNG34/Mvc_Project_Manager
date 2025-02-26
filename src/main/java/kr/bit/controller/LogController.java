package kr.bit.controller;

import kr.bit.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/menu/log")
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService){
        this.logService=logService;
    }

    @GetMapping("/list")
    public String logList(@RequestParam(required = false) String date, Model model){
        List<String> dates;
        try {
            dates = logService.getAllLogDates();
        } catch (IOException e) {
            dates = List.of("로그 파일을 읽을 수 없습니다.");
        }

        model.addAttribute("dates", dates);

        return "menu/Log/list";
    }
    @GetMapping("/detailLog/{date}")
    @ResponseBody
    public ResponseEntity<List<String>> logDetail(@PathVariable("date") String date){
        if (date == null) {
            date = java.time.LocalDate.now().toString(); // 기본값: 오늘 날짜
        }

        List<String> logs;

        try {
            logs = logService.readLogsByDate(date);
            System.out.println(logs);
        } catch (IOException e) {
            logs = List.of("로그 파일을 읽을 수 없습니다.");
        }
        return ResponseEntity.ok(logs);
    }


}
