package kr.bit.sql;

import kr.bit.entity.Criteria;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ManagerSqlProvider {
    public String getAdmins(Map<String, Object> params) {
        Criteria criteria = (Criteria) params.get("criteria");

        // 기본 쿼리 문자열 생성
        String sql = new SQL() {{
            SELECT("*");
            FROM("admins");
            if (criteria.getType() != null && !criteria.getType().isEmpty()) {
                switch (criteria.getType()) {
                    case "id":
                        WHERE("id LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "name":
                        WHERE("name LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "created_at":
                        WHERE("created_at LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                }
            }
            ORDER_BY("admin_id DESC");
        }}.toString();

        // LIMIT 구문 추가
        sql += " LIMIT #{criteria.pageStart}, #{criteria.perPageNum}";

        return sql;
    }
    public String getTotalCount(Map<String, Object> params) {
        Criteria criteria = (Criteria) params.get("criteria");

        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("admins");
            if (criteria.getType() != null && !criteria.getType().isEmpty()) {
                switch (criteria.getType()) {
                    case "id":
                        WHERE("id LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "name":
                        WHERE("name LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "created_at":
                        WHERE("created_at LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                }
            }
        }}.toString();
    }

}
