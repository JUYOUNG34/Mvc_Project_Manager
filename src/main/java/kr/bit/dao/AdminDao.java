package kr.bit.dao;

import kr.bit.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDao {
    @Autowired
    private AdminMapper adminMapper;
}
