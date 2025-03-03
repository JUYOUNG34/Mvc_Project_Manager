package kr.bit.service;

import kr.bit.entity.Reports;
import kr.bit.mapper.ReportMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReportService {

    @Autowired
    private ReportMapper reportMapper;


    public List<Reports> getAllReports() {
        return reportMapper.getAllReports();
    }
}
