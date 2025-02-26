package kr.bit.mapper;

import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

public interface Statsmapper {
    @Select("select gender, count(*) as count " +
            "from user_profiles " +
            "group by gender")
    List<Map<String, Object>> getGenderStats();

    @Select("select count(*) as totalcount " +
            "from user_profiles")
    int getTotalUsers();

    @Select("select date_format(created_at, '%Y-%m') as month, " +
            "count(*) as count " +
            "from user_profiles " +
            "group by date_format(created_at, '%Y-%m') " +
            "order by month")
    List<Map<String, Object>> getUserMonthlyStats();
}