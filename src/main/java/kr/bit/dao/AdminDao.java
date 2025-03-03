package kr.bit.dao;

import kr.bit.entity.Admins;
import kr.bit.entity.Criteria;
import kr.bit.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDao {
    @Autowired
    private AdminMapper adminMapper;

    public List<Admins> getAdmins(Criteria criteria){
        return adminMapper.getAdmins(criteria);
    }

    public boolean registerAdmin(Admins admin) {
        return adminMapper.registerAdmin(admin) > 0;
    }

    public boolean updateAdmin(Admins admin) {
        return adminMapper.updateAdmin(admin) > 0;
    }

    public boolean deleteAdmin(int adminId) {
        return adminMapper.deleteAdmin(adminId) > 0;
    }

    public Admins getAdminById(int adminId) {
        return adminMapper.getAdminById(adminId);
    }

    public boolean isIdDuplicate(String id) {
        return adminMapper.countById(id) > 0;
    }
}