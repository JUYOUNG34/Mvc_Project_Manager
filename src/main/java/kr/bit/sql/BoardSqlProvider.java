package kr.bit.sql;

import java.util.Map;
import kr.bit.entity.Criteria;
import org.apache.ibatis.jdbc.SQL;

public class BoardSqlProvider {

    public String getBoards(Map<String, Object> params) {
        if (params == null) {
            throw new IllegalArgumentException("params cannot be null");
        }
        Criteria criteria = (Criteria) params.get("criteria");
        if (criteria == null) {
            throw new IllegalArgumentException("criteria cannot be null");
        }
        String sql = new SQL() {{
            SELECT("b.id, " +
                    "a.id AS admin_id, " +
                    "b.writer_id, " +
                    "b.admin_writer_id, " +
                    "b.title, " +
                    "b.content, " +
                    "b.created_at, " +
                    "b.heart_count, " +
                    "b.comment_count, " +
                    "b.is_notice, " +
                    "b.is_blind, " +
                    "b.report_count, " +
                    "u.nickname, " +
                    "a.name");
            FROM("boards b");
            LEFT_OUTER_JOIN("users u ON b.writer_id = u.user_id");
            LEFT_OUTER_JOIN("admins a ON b.admin_writer_id = a.admin_id");
            WHERE("(b.writer_id IS NOT NULL OR b.admin_writer_id IS NOT NULL)");
            if (criteria.getType() != null && !criteria.getType().isEmpty()) {
                switch (criteria.getType()) {
                    case "id":
                        AND();
                        WHERE("b.id LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "nickname":
                        AND();
                        WHERE("u.nickname LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "title":
                        AND();
                        WHERE("b.title LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "created_at":
                        AND();
                        WHERE("b.created_at LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                }
            }
            ORDER_BY("b.is_notice DESC, b.created_at DESC");
        }}.toString();
        sql += " LIMIT #{criteria.pageStart}, #{criteria.perPageNum}";
        return sql;
    }

    public String getTotalCount(Map<String, Object> params) {
        if (params == null) {
            throw new IllegalArgumentException("params cannot be null");
        }
        Criteria criteria = (Criteria) params.get("criteria");
        if (criteria == null) {
            throw new IllegalArgumentException("criteria cannot be null");
        }
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("boards b");
            LEFT_OUTER_JOIN("users u ON b.writer_id = u.user_id");
            LEFT_OUTER_JOIN("admins a ON b.admin_writer_id = a.admin_id");
            WHERE("(b.writer_id IS NOT NULL OR b.admin_writer_id IS NOT NULL)");
            if (criteria.getType() != null && !criteria.getType().isEmpty()) {
                switch (criteria.getType()) {
                    case "id":
                        AND();
                        WHERE("b.writer_id LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "nickname":
                        AND();
                        WHERE("u.nickname LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "title":
                        AND();
                        WHERE("b.title LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                    case "created_at":
                        AND();
                        WHERE("b.created_at LIKE CONCAT('%', #{criteria.keyword}, '%')");
                        break;
                }
            }
        }}.toString();
    }



}
