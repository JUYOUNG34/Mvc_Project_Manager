package kr.bit.service;

import kr.bit.entity.Admins;
import kr.bit.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;

    public Admins adminLogin(Admins admin) {
        return loginMapper.getLoginAdmin(admin);
    }
}