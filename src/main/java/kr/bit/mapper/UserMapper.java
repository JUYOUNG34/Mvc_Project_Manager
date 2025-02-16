package kr.bit.mapper;

import kr.bit.entity.*;
import kr.bit.sql.UserSqlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface UserMapper {

    @SelectProvider(type = UserSqlProvider.class, method = "getUser_profiles")
    List<User_profiles> getUser_profiles(@Param("criteria") Criteria criteria);
    @SelectProvider(type = UserSqlProvider.class, method = "getTotalCount")
    int getTotalCount(@Param("criteria") Criteria criteria);

    @Select("select * from user_profiles natural join users where user_id = #{user_id} ")
    User_profiles oneUser_profile(@Param("user_id") int user_id);
    @Select("select * from points where user_id = #{user_id}")
    Points onePoint(@Param("user_id")int user_id);
    @Select("select * from user_hobbies natural join hobbies where hobby_id = id and user_id = #{user_id}")
    List<Hobbies> getHobbies(@Param("user_id")int user_id);
    @Select("select * from chat_rooms natural join user where participant_1_id = #{user_id} or participant_2_id = #{user_id}")
    List<Chat_rooms> getChat_rooms(@Param("user_id")int user_id);


}
