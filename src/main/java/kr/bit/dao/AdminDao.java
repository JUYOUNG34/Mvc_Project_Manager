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

}
