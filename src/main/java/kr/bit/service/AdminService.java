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
    public int getTotalCount(Criteria criteria){return adminDao.getTotalCount(criteria);}

    public Admins oneAdmin(String id){
        return adminDao.oneAdmin(id);
    }
    // 사번 자동 생성 메서드
    public String generateEmployeeNumber() {
        int lastEmployeeNumber = adminDao.getLastEmployeeNumber(); // 마지막 사번 조회
        return String.format("EMP%04d", lastEmployeeNumber + 1);
    }

    // 관리자 등록 메서드
    public boolean registerAdmin(Admins admin) {

//        String hashedPassword = passwordEncoder.encode(admin.getPass()); // 비밀번호 해싱
//        admin.setPass(hashedPassword);
        admin.setEmployee_number(generateEmployeeNumber());
        return adminDao.insertAdmin(admin) > 0;
    }
    public boolean updateAdmin(Admins admin){
//        String hashedPassword = passwordEncoder.encode(admin.getPass());
//        admin.setPass(hashedPassword);
        return adminDao.updateAdmin(admin) > 0;
    }

    public boolean deleteAdmin(String id){
        return adminDao.deleteAdmin(id) > 0;
    }

    public String getAdminName(int id){return adminDao.getAdminName(id);}
}