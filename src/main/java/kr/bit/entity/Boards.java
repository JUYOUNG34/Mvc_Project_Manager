package kr.bit.entity;





import lombok.Data;

@Data
public class Boards {
    private int id;

    private String admin_id;
    private int heart_count;
    private int comment_count;
    private int report_count;
    private int writer_id;
    private int admin_writer_id;
    private String nickname;
    private String name;
    private String title;
    private String content;
    private String created_at;
    private int is_notice;
    private int is_blind;

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
