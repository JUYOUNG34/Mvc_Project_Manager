package kr.bit.mapper;

import kr.bit.entity.Blacklist;
import kr.bit.entity.Criteria;
import kr.bit.entity.Events;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlacklistMapper {

    @Select("select * from user_blacklists")
    List<Blacklist> getAllBlack();

    @Select("select b.id, b.blocked_user_id as userId, u.nickname, b.blocked_at as blockedAt, " +
            "r.report_content as reportReason " +
            "from user_blacklists b " +
            "join users u on b.blocked_user_id = u.user_id " +
            "join reports r on b.report_id = r.id " +
            "where (#{criteria.type} = 'nickname' AND u.nickname LIKE CONCAT('%', #{criteria.keyword}, '%')) " +
            "   OR (#{criteria.type} = 'userId' AND CAST(b.blocked_user_id AS CHAR) LIKE CONCAT('%', #{criteria.keyword}, '%')) " +
            "   OR #{criteria.keyword} = '' " +
            "order by b.blocked_at desc " +
            "LIMIT #{criteria.pageStart}, #{criteria.perPageNum}")
    List<Blacklist> getBlacklist(@Param("criteria") Criteria criteria);

    @Select("SELECT COUNT(*) FROM user_blacklists b " +
            "JOIN users u ON b.blocked_user_id = u.user_id " +
            "JOIN reports r ON b.report_id = r.id " +
            "WHERE (#{criteria.type} = 'nickname' AND u.nickname LIKE CONCAT('%', #{criteria.keyword}, '%')) " +
            "   OR (#{criteria.type} = 'userId' AND CAST(b.blocked_user_id AS CHAR) LIKE CONCAT('%', #{criteria.keyword}, '%')) " +
            "   OR #{criteria.keyword} = ''")
    int getTotalCount(@Param("criteria") Criteria criteria);

    @Delete("DELETE FROM user_blacklists WHERE id = #{id}")
    int removeFromBlacklist(@Param("id") int id);

//    @Insert("insert into user_blacklists() values ()")

}