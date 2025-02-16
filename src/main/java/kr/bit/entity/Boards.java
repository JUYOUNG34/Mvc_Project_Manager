package kr.bit.entity;


import lombok.Data;

@Data
public class Boards {
    private int id;
    private int heart_count;
    private int comment_count;
    private int writer_id ;
    private String title ;
    private String content ;
    private String created_at ;
    private Boolean is_notice ;
    private Boolean is_blind ;
    private int report_count ;
}
