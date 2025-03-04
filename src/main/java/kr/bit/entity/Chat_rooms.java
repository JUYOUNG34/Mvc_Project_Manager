package kr.bit.entity;

import lombok.Data;

@Data
public class Chat_rooms {
    private int id;
    private int man_id;
    private int woman_id;
    private boolean man_enter;
    private boolean man_continue;
    private boolean woman_enter;
    private boolean woman_continue;
    private String session_status;
    private String end_time;
    private String man_out;
    private String woman_out;
    private String created_at;
    private boolean is_reported;
    private String last_updated;
    private String receiver_id;
    private String nickname; // 추가한 필드
}
