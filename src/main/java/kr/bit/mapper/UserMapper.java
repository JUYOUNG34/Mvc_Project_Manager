package kr.bit.mapper;

import kr.bit.entity.*;
import kr.bit.sql.UserSqlProvider;
import org.apache.ibatis.annotations.*;

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
    @SelectProvider(type = UserSqlProvider.class, method = "getChat_rooms")
    List<Chat_rooms> getChat_rooms(@Param("user_id")int user_id,@Param("criteria")Criteria criteria);
    @SelectProvider(type = UserSqlProvider.class, method = "getChat_roomsCount")
    int getChat_roomsCount(@Param("user_id")int user_id,@Param("criteria")Criteria criteria);

    @Update("update points set points = #{points}, reading_glass = #{reading_glass}, firewood = #{firewood} where user_id = #{user_id} ")
    int modifyPoints(Points points);
    @Update("update user_profiles set photo_image_url = #{photo_image_url} where user_id = #{user_id}")
    int modifyPhoto_image_url(@Param("photo_image_url")String photo_image_url,@Param("user_id") int user_id);


    @Select("select * from messages m join users u on m.user_id = u.user_id where m.room_id = #{room_id}")
    List<Messages> getMessages(@Param("room_id") int room_id);

    @Select("select * from users where user_id = (select distinct receiver_id from messages where room_id = #{room_id} and user_id = #{user_id}) ")
    Users oneUser(@Param("room_id")int room_id,@Param("user_id")int user_id);

}
