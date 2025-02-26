package kr.bit.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Blacklist {
    private int id;
    private int userId;
    private String nickname;
    private Date blockedAt;
    private String reportReason;
}