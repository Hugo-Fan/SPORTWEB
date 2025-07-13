package com.hugo.blog.mapper;


import com.hugo.blog.model.po.UsersPO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.*;

import java.util.List;
public interface UserMapper {


    //用姓名查詢user資料
    @Select("""
            select *
            from users
            where username= #{username}
            """)
    @Results(id = "userdata" ,value = {
            @Result(id = true,column = "user_id",property = "user_id"),

    })
    UsersPO gitUserData(String username);

    // 檢查name和password在資料庫內是否有
    @Select("""
            select count(*) 
            from users
            where username= #{username} and password =#{password}
            """)
      // 因為回傳的是基本數據類型所以不用映射
//    @Results(id = "check" ,value = {
//            @Result(id = true,column = "user_id",property = "user_id"),
//
//    })
    boolean checkNameAndPassword(String username,String password);

    //用姓名和信箱查詢確認是否有這個用戶資料
    @Select("""
            select count(*) 
            from users 
            where username =#{username} and email = #{email};
            """)
    boolean queryByPasswor(UsersPO usersPO);

    @Select("""
            select count(*) 
            from users
            where username= #{username}
            """)
    boolean checkuser(String username);

    // 註冊帳號
    @Insert("""
            insert into users(username, email, password,sex,age)
            values(#{username},#{email},#{password},#{sex},#{age})
            """)
    boolean addUsers(UsersPO usersPO);


    // 修改userdata
    @Update("""
            update users set  email = #{email} , password = #{password} , sex = #{sex} , age = #{age}
            where user_id = #{user_id}
            """)
    // 因為回傳的是基本數據類型所以不用映射
//    @Results(id = "userdata" ,value = {
//            @Result(id = true,column = "user_id",property = "user_id"),
//
//    })
    boolean editUserData(UsersPO usersPO);


}
