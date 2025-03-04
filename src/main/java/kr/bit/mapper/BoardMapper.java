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


 @Update("update boards set is_blind = true where id = #{id}")
 int blockBoard(int id);

 @Select("SELECT * FROM boards where id = #{id}")
 Boards getDetailNotice(@Param("id") int id);

 @Select("SELECT * FROM boards WHERE is_notice = 1")
 List<Boards> getAllNotices();

 @Insert("insert into boards (title,content,is_notice,admin_writer_id) values (#{title},#{content},#{is_notice},#{admin_writer_id})")
 void insertNotice(Boards board);

 // 게시글 업데이트 쿼리 추가
 @Update("UPDATE boards SET title = #{title}, content = #{content} WHERE id = #{id}")
 int updateBoard(Boards board);  // Board 객체를 받아 해당 id의 title과 content를 업데이트

 // 공지사항 삭제
 @Delete("DELETE FROM boards WHERE id = #{id}")
 void deleteNotice(int id);

// // 아이디(작성자)로 공지사항 검색 쿼리 추가
// @Select("SELECT * FROM boards WHERE is_notice = 1 AND admin_writer_id IN " +
//         "(SELECT admin_id FROM admins WHERE admin_name LIKE CONCAT('%', #{keyword}, '%'))")
// List<Boards> searchNoticesByWriterId(@Param("keyword") String keyword);

 // 아이디(작성자)로 공지사항 검색 쿼리 추가
 @Select("SELECT * FROM boards WHERE is_notice = 1 AND admin_writer_id IN " +
         "(SELECT admin_id FROM admins WHERE name LIKE CONCAT('%', #{keyword}, '%'))")
 List<Boards> searchNoticesByNickname(@Param("keyword") String keyword);

 // 제목으로 공지사항 검색 쿼리 추가
 @Select("SELECT * FROM boards WHERE is_notice = 1 AND title LIKE CONCAT('%', #{keyword}, '%')")
 List<Boards> searchNoticesByTitle(@Param("keyword") String keyword);
}
