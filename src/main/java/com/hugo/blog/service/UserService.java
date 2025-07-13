package com.hugo.blog.service;

import com.hugo.blog.model.dto.UsersDTO;

/**
 *  article service
 *
 */
public interface UserService {

    // user登入
    boolean userLogin(UsersDTO usersDTO);

    // 註冊user帳號
    boolean addUser(UsersDTO usersDTO);
    // 使用username查詢userdata
    UsersDTO queryByUsername(String username);

    // 使用username和信箱查詢password
    boolean queryByPwd(UsersDTO usersDTO);

    // 檢查username是否重複

    boolean checkUser(String usename);
    // 修改userdata
    Boolean editUserData(UsersDTO usersDTO);


}
