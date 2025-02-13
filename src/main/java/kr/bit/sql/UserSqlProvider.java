package kr.bit.sql;

import java.util.Map;
import kr.bit.entity.Criteria;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider {
    public String getUser_profiles(Map<String, Object> params) {
        Criteria criteria = (Criteria) params.get("criteria");

        // 기본 쿼리 문자열 생성
        String sql = new SQL() {{
            SELECT("*");
            FROM("user_profiles natural join users");
            if (criteria.getType() != null && !criteria.getType().isEmpty()) {
                switch (criteria.getType()) {
                    case "user_id":
                        WHERE("user_id LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "nickname":
                        WHERE("nickname LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "created_at":
                        WHERE("created_at LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                }
            }
            ORDER_BY("user_id DESC");
        }}.toString();

        // LIMIT 구문 추가
        sql += " LIMIT #{criteria.pageStart}, #{criteria.perPageNum}";

        return sql;
    }
    public String getTotalCount(Map<String, Object> params) {
        Criteria criteria = (Criteria) params.get("criteria");

        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("user_profiles natural join users");
            if (criteria.getType() != null && !criteria.getType().isEmpty()) {
                switch (criteria.getType()) {
                    case "user_id":
                        WHERE("user_id LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "nickname":
                        WHERE("nickname LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "created_at":
                        WHERE("created_at LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                }
            }
        }}.toString();
    }


}
