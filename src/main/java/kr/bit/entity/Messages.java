package kr.bit.entity;

import lombok.Data;

@Data
public class Messages {
    private int id;
    private int room_id;
    private int user_id;
    private int receiver_id;
    private String message_content;
    private String created_at;
    private boolean is_read;

    private String nickname;
    private String topic;
}
