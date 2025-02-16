package kr.bit.entity;

import lombok.Data;

@Data
public class Chat_rooms {
    private int id;
    private int participant_1_id;
    private int participant_2_id;
    private boolean is_enter_1;
    private boolean is_continue_1;
    private boolean is_enter_2;
    private boolean is_continue_2;
    private String session_status;
    private String end_time;

}
