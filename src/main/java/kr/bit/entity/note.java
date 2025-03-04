package kr.bit.entity;

import lombok.Data;

@Data
public class note {
    private int id;
    private int sender_id ;
    private int  receiver_id;
    private String title;
    private String content;
    private String created_at;
    private boolean is_read ;
}
