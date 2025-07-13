package com.hugo.blog.model.po;

import lombok.Data;

@Data
public class SportlistPO {
    private Integer sport_id;
    private Integer user_id;
    private String actiongroup;
    private String date;
    private String sportlist;

}
