package kr.bit.entity;

import lombok.Data;


import java.time.LocalDate;

@Data
public class Events {
    private int id;
    private String name;
    private String image_url;
    private String start_date;
    private String end_date;
}