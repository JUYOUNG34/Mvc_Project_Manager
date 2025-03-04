package kr.bit.entity;

import lombok.Data;


@Data
public class Reports {
    private int id;
    private int reporter_id;
    private int reported_id;
    private String report_type;
    private int current_id;
    private String report_content;
    private String report_date;
    private String status;
}
