package kr.bit.mapper;


import kr.bit.entity.Reports;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReportMapper {

    @Select("select id,reporter_id,reported_id, report_date, status " +
            "from reports;")
    List<Reports> getAllReports();
}
