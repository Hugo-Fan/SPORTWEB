package com.hugo.blog.model.po;

import lombok.Data;

@Data
public class UsersPO {
    private Integer user_id;
    private String username;
    private String email;
    private String password;
    private String sex;
    private Integer age;
}
