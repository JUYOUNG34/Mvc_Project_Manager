package kr.bit.mapper;

import kr.bit.entity.Boards;

import kr.bit.entity.Criteria;
import kr.bit.sql.BoardSqlProvider;
import org.apache.ibatis.annotations.*;

import kr.bit.entity.Events;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface BoardMapper {

   @SelectProvider(type = BoardSqlProvider.class , method = "getBoards")
    List<Boards> getBoards(@Param("criteria") Criteria criteria);
   @SelectProvider(type = BoardSqlProvider.class,method = "getTotalCount")
    int getTotalCount(@Param("criteria") Criteria criteria);
   @Select("select * from boards b join users u on b.writer_id = u.user_id where user_id=#{user_id}")
    Boards getDetailBoard(@Param("user_id") int user_id);
    @Update("UPDATE boards SET is_blind = CASE WHEN is_blind = true THEN false ELSE true END WHERE writer_id = #{user_id}")
    int updateBoardBlind(@Param("user_id") int user_id);
    @Select("SELECT " +
            "b.id, " +
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
            "a.name " +
            "FROM boards b " +
            "LEFT JOIN admins a ON b.admin_writer_id = a.admin_id " +
            "WHERE b.admin_writer_id = #{admin_writer_id}")
    Boards getDetailNotice(@Param("admin_writer_id") int admin_writer_id);

    @Update("update boards set is_blind = true where id = #{id}")
    int blockBoard(int id);

}
