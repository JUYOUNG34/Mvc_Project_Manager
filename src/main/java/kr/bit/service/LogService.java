package kr.bit.service;

import kr.bit.dao.LogDao;
import kr.bit.dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    @Autowired
    private LogDao logDao;
}
