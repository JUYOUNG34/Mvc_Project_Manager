package kr.bit.mapper;

import kr.bit.entity.Blacklist;
import kr.bit.entity.Criteria;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlacklistMapper {

    @Select("select b.id, b.blocked_user_id, u.nickname, b.blocked_at " +
            "from user_blacklists b " +
            "join users u on b.blocked_user_id = u.user_id " +
            "where (#{criteria.type} = 'nickname' AND u.nickname LIKE CONCAT('%', #{criteria.keyword}, '%')) " +
            "   OR (#{criteria.type} = 'userId' AND CAST(b.blocked_user_id AS CHAR) LIKE CONCAT('%', #{criteria.keyword}, '%')) " +
            "   OR #{criteria.keyword} = '' " +
            "order by b.blocked_at desc " +
            "LIMIT #{criteria.pageStart}, #{criteria.perPageNum}")
    List<Blacklist> getBlacklist(@Param("criteria") Criteria criteria);

    @Select("SELECT COUNT(*) FROM user_blacklists b " +
            "JOIN users u ON b.blocked_user_id = u.user_id " +
            "WHERE (#{criteria.type} = 'nickname' AND u.nickname LIKE CONCAT('%', #{criteria.keyword}, '%')) " +
            "   OR (#{criteria.type} = 'userId' AND CAST(b.blocked_user_id AS CHAR) LIKE CONCAT('%', #{criteria.keyword}, '%')) " +
            "   OR #{criteria.keyword} = ''")
    int getTotalCount(@Param("criteria") Criteria criteria);

    @Delete("DELETE FROM user_blacklists WHERE id = #{id}")
    int removeFromBlacklist(@Param("id") int id);

    @Insert("insert into user_blacklists(blocked_user_id,blocker_id) values (#{user_id},#{admin_id})")
    int blockUser(@Param("user_id") int user_id, @Param("admin_id")int admin_id);

    @Select("select id from user_blacklists where blocked_user_id = #{blocked_user_id} ")
    Integer oneBlockUserID(@Param("blocked_user_id") int blocked_user_id);

    @Delete("DELETE FROM user_blacklists WHERE blocked_user_id = #{blocked_user_id}")
    int blockCancel(@Param("blocked_user_id") int blocked_user_id);
}