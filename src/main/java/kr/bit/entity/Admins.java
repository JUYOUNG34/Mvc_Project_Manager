package kr.bit.entity;

import lombok.Data;

@Data
public class Admins {
    private int admin_id;
    private String id;
    private String pass;
    private String name;
    private String employee_number;
    private String created_at;
    private String admin_level;

    private static boolean isLoggedIn = false;

    public static void setLoginStatus(boolean status) {
        isLoggedIn = status;
    }

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }
}