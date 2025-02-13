package kr.bit.entity;

import lombok.Data;

@Data
public class User_profiles {
    private int user_id ;
    private String gender;
    private String birth_date;
    private String created_at;
    private int height;
    private int weight;
    private String photo_image_url;
    private int profile_image_id;
    private String religion;
    private String mbti;
    private String drinking_level;
    private String smoking_status;
    //users table 에서 조인해서 가져올 값
    private String nickname;
    private String google_id;
    private String kakao_id;
    private String naver_id;


}
