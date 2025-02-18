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
    public String getChat_rooms(Map<String, Object> params) {
        Criteria criteria = (Criteria) params.get("criteria");

        // 기본 쿼리 문자열 생성
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM users u join chat_rooms cr on cr.participant_1_id = u.user_id or cr.participant_2_id = u.user_id ");
        sql.append("WHERE user_id = #{user_id} ");

        if (criteria.getType() != null && !criteria.getType().isEmpty()) {
            switch (criteria.getType()) {
                case "nickname":
                    sql.append("AND nickname LIKE CONCAT('%', #{criteria.keyword}, '%') ");
                    break;
                case "created_at":
                    sql.append("AND created_at LIKE CONCAT('%', #{criteria.keyword}, '%') ");
                    break;
            }
        }
        sql.append("ORDER BY created_at DESC ");

        // LIMIT 구문 추가
        sql.append("LIMIT #{criteria.pageStart}, #{criteria.perPageNum}");

        return sql.toString();
    }
    public String getChat_roomsCount(Map<String, Object> params) {
        Criteria criteria = (Criteria) params.get("criteria");

        // 기본 쿼리 문자열 생성
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM users u join chat_rooms cr on cr.participant_1_id = u.user_id or cr.participant_2_id = u.user_id ");
        sql.append("WHERE user_id = #{user_id} ");

        if (criteria.getType() != null && !criteria.getType().isEmpty()) {
            switch (criteria.getType()) {
                case "nickname":
                    sql.append("AND nickname LIKE CONCAT('%', #{criteria.keyword}, '%') ");
                    break;
                case "created_at":
                    sql.append("AND created_at LIKE CONCAT('%', #{criteria.keyword}, '%') ");
                    break;
            }
        }
        sql.append("ORDER BY created_at DESC ");

        // LIMIT 구문 추가
        sql.append("LIMIT #{criteria.pageStart}, #{criteria.perPageNum}");

        return sql.toString();
    }


}
