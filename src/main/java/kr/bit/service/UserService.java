package kr.bit.service;

import kr.bit.dao.UserDao;
import kr.bit.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public List<User_profiles> getUser_profiles(Criteria criteria){
        return userDao.getUser_profiles(criteria);
    }
    public int getTotalCount(Criteria criteria){return userDao.getTotalCount(criteria);}


    public User_profiles oneUser_profile(int user_id){
        return userDao.oneUser_profile(user_id);
    }
    public Points onePoint(int user_id){
        return userDao.onePoint(user_id);
    }
    public String getHobbies(int user_id){
        List<Hobbies> hobbies = userDao.getHobbies(user_id);
        // 리스트의 요소들을 문자열로 변환
        String hobbiesStr = hobbies.stream()
                .map(Hobbies::getName) // Hobbies 클래스의 getName 메서드를 사용
                .collect(Collectors.joining(", "));

        return hobbiesStr;
    }
    public List<Chat_rooms> getChat_rooms(int user_id){
        return userDao.getChat_rooms(user_id);
    }

}
