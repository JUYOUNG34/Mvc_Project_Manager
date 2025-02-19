package kr.bit.service;

import kr.bit.dao.AdminDao;
import kr.bit.entity.Admins;
import kr.bit.entity.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminDao adminDao;

    public List<Admins> getAdmins(Criteria criteria){
        return adminDao.getAdmins(criteria);
    }




}
