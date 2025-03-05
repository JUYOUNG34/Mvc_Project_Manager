package kr.bit.entity;

import lombok.Data;

@Data
public class Blacklist {
    private int id;
    private int blocked_user_id;
    private String nickname;
    private String blocked_at;

}