package kr.bit.entity;

import lombok.Data;

@Data
public class Users {
    private int user_id;
    private String google_id;
    private String kakao_id;
    private String naver_id;
    private String nickname;
}
