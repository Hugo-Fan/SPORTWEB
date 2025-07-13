package com.hugo.blog.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SportlistDTO {
    private Integer sport_id;
    private Integer user_id;
    private String actiongroup;
    private String date;
    private String sportlist;
    private List<String> sportlists;
}
