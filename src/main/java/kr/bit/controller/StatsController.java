package kr.bit.controller;

import kr.bit.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/stats")
    public String stats(Model model) {
        try {

            int totalUsers = statsService.getTotalUsers();
            model.addAttribute("totalUsersData", totalUsers);


            Map<String, Integer> genderStats = statsService.getGenderStats();
            model.addAttribute("maleUsersData", genderStats.get("male"));
            model.addAttribute("femaleUsersData", genderStats.get("female"));


            List<Map<String, Object>> userStats = statsService.getUserMonthlyStats();
            model.addAttribute("userStatsData", userStats);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("totalUsersData", 0);
            model.addAttribute("maleUsersData", 0);
            model.addAttribute("femaleUsersData", 0);
            model.addAttribute("userStatsData", "[]");
        }

        return "menu/stats";
    }
}