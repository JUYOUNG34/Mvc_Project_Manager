package kr.bit.dao;

import kr.bit.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {
    @Autowired
    private LoginMapper loginMapper;

}
