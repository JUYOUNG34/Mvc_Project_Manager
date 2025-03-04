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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Admins> getAdmins(Criteria criteria){
        return adminDao.getAdmins(criteria);
    }

    public boolean registerAdmin(Admins admin) {
        // 비밀번호 암호화
//        admin.setPass(passwordEncoder.encode(admin.getPass()));
        return adminDao.registerAdmin(admin);
    }

    public boolean updateAdmin(Admins admin) {
        // 비밀번호 암호화
//        if (admin.getPass() != null && !admin.getPass().isEmpty()) {
//            admin.setPass(passwordEncoder.encode(admin.getPass()));
//        }
        return adminDao.updateAdmin(admin);
    }

    public boolean deleteAdmin(int adminId) {
        return adminDao.deleteAdmin(adminId);
    }

    public Admins getAdminById(int adminId) {
        return adminDao.getAdminById(adminId);
    }

    public boolean isIdDuplicate(String id) {
        return adminDao.isIdDuplicate(id);
    }

    public String getAdminName(int id){return adminDao.getAdminName(id);}
}