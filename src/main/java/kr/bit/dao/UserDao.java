package kr.bit.dao;

import kr.bit.entity.*;
import kr.bit.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private UserMapper userMapper;

    public List<User_profiles> getUser_profiles(Criteria criteria){
        return userMapper.getUser_profiles(criteria);
    }
    public int getTotalCount(Criteria criteria){return userMapper.getTotalCount(criteria);}

    public User_profiles oneUser_profile(int user_id){
        return userMapper.oneUser_profile(user_id);
    }
    public Points onePoint(int user_id){
        return userMapper.onePoint(user_id);
    }
    public List<Hobbies> getHobbies(int user_id){
        return userMapper.getHobbies(user_id);
    }
    public List<Chat_rooms> getChat_rooms(int user_id, Criteria criteria){
        return userMapper.getChat_rooms(user_id,criteria);
    }
    public int getChat_roomsCount(int user_id, Criteria criteria){
        return userMapper.getChat_roomsCount(user_id,criteria);
    }

    public int modifyPoints(Points points){
        return userMapper.modifyPoints(points);
    }
    public int modifyPhoto_image_url(String photo_image_url, int user_id){
        return userMapper.modifyPhoto_image_url(photo_image_url,user_id);
    }

    public List<Messages> getMessages(int room_id){
        return userMapper.getMessages(room_id);
    }
    public Users oneUser(int user_id){
        return userMapper.oneUser(user_id);
    }
    public int receiveUser(int user_id,int room_id){return userMapper.receiveUser(user_id,room_id);}

}
