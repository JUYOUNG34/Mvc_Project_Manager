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
    public List<Chat_rooms> getChat_rooms(int user_id){
        return userMapper.getChat_rooms(user_id);
    }

}
