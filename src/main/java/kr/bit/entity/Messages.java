package kr.bit.entity;

import lombok.Data;

@Data
public class Messages {
    private int id;
    private int room_id;
    private int user_id;
    private String message_context;
    private String created_at;
}
