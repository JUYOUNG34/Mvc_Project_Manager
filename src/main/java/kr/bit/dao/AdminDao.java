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
    public int getTotalCount(Criteria criteria){return adminMapper.getTotalCount(criteria);}

    public Admins oneAdmin(String id){
        return adminMapper.oneAdmin(id);
    }
    // 마지막 사번 조회 메서드
    public int getLastEmployeeNumber() {
        String lastEmployeeNumber = adminMapper.getLastEmployeeNumber();
        if (lastEmployeeNumber == null) {
            return 0; // 초기 값 설정 (없을 경우)
        }
        return Integer.parseInt(lastEmployeeNumber.replace("EMP", ""));
    }

    public int insertAdmin(Admins admin) {
        return adminMapper.insertAdmin(admin);
    }
    public int updateAdmin(Admins admin){
        return adminMapper.updateAdmin(admin) ;
    }
    public int deleteAdmin(String id){
        return adminMapper.deleteAdmin(id);
    }

    public String getAdminName(int id){return adminMapper.getAdminName(id);}
}