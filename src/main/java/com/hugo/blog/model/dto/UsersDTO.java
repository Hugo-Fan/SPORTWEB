package com.hugo.blog.model.dto;

import lombok.Data;

@Data
public class UsersDTO {
    private Integer user_id;
    private String username;
    private String email;
    private String password;
    private String sex;
    private Integer age;

}
