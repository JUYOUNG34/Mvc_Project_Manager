package kr.bit.mapper;

import com.mysql.cj.protocol.x.Notice;
import kr.bit.entity.Boards;
import kr.bit.entity.Reports;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NoticeMapper {
    @Select("SELECT * FROM boards b join users u on b.writer_id= u.user_id WHERE b.writer_id = u.user_id;")
    Boards getDetailNotice(@Param("id") int id);

    @Select("SELECT id, writer_id, title, created_at, is_notice " +
            "FROM boards WHERE is_notice = 1")
    List<Boards> getAllNotices();
}


