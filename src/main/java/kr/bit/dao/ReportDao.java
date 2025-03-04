package kr.bit.dao;

import kr.bit.entity.Criteria;
import kr.bit.entity.Messages;
import kr.bit.entity.Reports;
import kr.bit.mapper.ReportMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportDao {


    @Autowired
    private ReportMapper reportMapper;

    public List<Reports> getReports(String searchType,String searchKeyword)  {
        return reportMapper.getReports(searchType,searchKeyword);
    }

    public int getTotalCount(Criteria criteria) {
        return reportMapper.getTotalCount(criteria);
    }

    public Reports getDetailReports(int id) {
        return reportMapper.getDetailReports(id);
    }

    public void updateReportStatus(int id, String changeStatus) {
        reportMapper.updateReportStatus(id, changeStatus);
    }

    // 신고 내역 삭제
    public void deleteReport(int id) {
        reportMapper.deleteReport(id);  // ReportMapper의 deleteReport 메서드 호출
    }
//    public int getCurrentId(@Param("id") int id){ return reportMapper.getCurrentId(id); }
//
//
//     public List<Messages> getMessages(@Param("room_id") int room_id){
//        return reportMapper.getMessages(room_id);
//     }
public List<Reports> getCurrentId(@Param("id") int id){ return reportMapper.getCurrentId(id); }


    public List<Messages> getMessages(@Param("room_id") int room_id){
        return reportMapper.getMessages(room_id);
    }




}
