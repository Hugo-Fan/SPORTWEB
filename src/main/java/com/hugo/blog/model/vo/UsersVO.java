package com.hugo.blog.model.vo;

import lombok.Data;

@Data
public class UsersVO {
    private Integer user_id;
    private String username;
    private String email;
    private String password;
    private String sex;
    private Integer age;
}
