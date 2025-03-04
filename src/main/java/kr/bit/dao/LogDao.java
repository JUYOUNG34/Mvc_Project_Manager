package kr.bit.dao;

import kr.bit.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LogDao {
    @Autowired
    private LogMapper logMapper;
}
