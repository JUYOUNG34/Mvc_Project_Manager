package kr.bit.service;


import kr.bit.dao.ReportDao;
import kr.bit.entity.Criteria;
import kr.bit.entity.Messages;
import kr.bit.entity.Reports;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportDao reportDao;

    public List<Reports> getReports(String searchType, String searchKeyword) {
        return reportDao.getReports(searchType,searchKeyword);
    }
    public int getTotalCount(Criteria criteria) {
        return reportDao.getTotalCount(criteria);
    }

    public Reports getDetailReports(int id) {
        return reportDao.getDetailReports(id);
    }

    public void updateReportStatus(int id, String changeStatus) {
        reportDao.updateReportStatus(id, changeStatus);
    }

    // 신고 내역 삭제
    public void deleteReport(int id) {
        reportDao.deleteReport(id);  // 신고 내역 삭제 DAO 호출
    }

//    public int getCurrentId(@Param("id") int id){ return reportDao.getCurrentId(id); }
public List<Reports> getCurrentId(@Param("id") int id){ return reportDao.getCurrentId(id); }

    public List<Messages> getMessages(@Param("room_id") int room_id){
        return reportDao.getMessages(room_id);
    }


}


