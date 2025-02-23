package kr.bit.service;

import kr.bit.mapper.Statsmapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {

    @Autowired
    private Statsmapper statsmapper;

    // 전체 사용자 수 조회
    public int getTotalUsers() {
        try {
            return statsmapper.getTotalUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 성별 통계 조회
    public Map<String, Integer> getGenderStats() {
        Map<String, Integer> result = new HashMap<>();

        // 기본값 설정
        result.put("male", 0);
        result.put("female", 0);

        try {
            List<Map<String, Object>> genderStats = statsmapper.getGenderStats();

            // 조회 결과 매핑
            if (genderStats != null) {
                for (Map<String, Object> stat : genderStats) {
                    String gender = (String) stat.get("gender");
                    Object countObj = stat.get("count");

                    // null 체크 추가
                    if (gender != null && countObj != null) {
                        Integer count = (countObj instanceof Number) ? ((Number) countObj).intValue() : 0;

                        // gender 값이 "male"과 "female"로 저장됨
                        if ("male".equals(gender)) {
                            result.put("male", count);
                        } else if ("female".equals(gender)) {
                            result.put("female", count);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // 월별 가입자 통계 조회
    public List<Map<String, Object>> getUserMonthlyStats() {
        try {
            return statsmapper.getUserMonthlyStats();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}