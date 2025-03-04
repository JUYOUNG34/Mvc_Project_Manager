package kr.bit.mapper;

import kr.bit.entity.Criteria;
import kr.bit.entity.Messages;
import kr.bit.entity.Reports;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ReportMapper {


    @Select("<script>" +
            "SELECT * FROM reports " +
            "<where>" +
            "   <if test='searchKeyword != null and searchKeyword != \"\" and searchType != null and searchType != \"\"'>" +
            "       ${searchType} = #{searchKeyword}" +
            "   </if>" +
            "</where>" +
            "ORDER BY report_date DESC" +
            "</script>")
    List<Reports> getReports(@Param("searchType") String searchType, @Param("searchKeyword") String searchKeyword);


    // 특정 사용자의 공지사항 상세 조회
    @Select("SELECT * FROM reports WHERE id = #{id}")
    Reports getDetailReports(@Param("id") int report_Id);

    @Update("UPDATE reports" +
            "SET status = #{changeStatus} " +
            "WHERE " +
            " id = #{id}")
    void updateReportStatus(@Param("id") int id, String changeStauts);

    // 공지사항 총 개수 조회 (검색어 포함)
    @Select("<script>" +
            "SELECT COUNT(*) FROM reports" +
//            "<where>" +
//            "   <if test='criteria.searchKeyword != null and criteria.searchKeyword != \"\"'> " +
//            "       AND title LIKE CONCAT('%', #{criteria.searchKeyword}, '%') " +
//            "   </if>" +
//            "</where>" +
            "</script>")
    int getTotalCount(@Param("criteria") Criteria criteria);

    // 신고 내역 삭제
    @Delete("DELETE FROM reports WHERE id = #{id}")
    void deleteReport(int id);

//    @Select("select current_id from reports where id = #{id}")
//    int getCurrentId(@Param("id") int id);
//
//    @Select("select * from messages where room_id = #{room_id}")
//    List<Messages> getMessages(@Param("room_id") int room_id);

    @Select("SELECT * FROM reports WHERE id = #{id}")
    List<Reports> getCurrentId(@Param("id") int id);

    @Select("SELECT " +
            " id" +
            " , room_id" +
            " , user_id" +
            " , CONVERT(message_content USING utf8) AS message_content" +
            " , created_at" +
            " , is_read" +
            " FROM messages " +
            " WHERE room_id = #{room_id}")
    List<Messages> getMessages(@Param("room_id") int room_id);

}


