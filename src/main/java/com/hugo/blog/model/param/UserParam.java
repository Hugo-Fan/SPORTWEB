package com.hugo.blog.model.param;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserParam {

    public static interface AddUser {
    }

    public static interface EditUser {
    }

    @NotNull(message = "修改時必須有id",groups = {EditUser.class})
    private Integer user_id;

    @NotNull(message = "新增新帳號需要輸入名稱", groups = {AddUser.class})
    @Size(min = 3, message = "帳號名稱長度必須大於等於{min}", groups = {AddUser.class})
    private String username;

    @NotNull(message = "請輸入密碼", groups = {AddUser.class, EditUser.class})
    @Size(min = 6, max = 20, message = "密碼長度在{min}~{max}", groups = {AddUser.class, EditUser.class})
    private String password;

    private Integer age;

    @NotBlank(message = "請輸入電子郵件", groups = {AddUser.class, EditUser.class})
    private String email;

    private String sex;



}
