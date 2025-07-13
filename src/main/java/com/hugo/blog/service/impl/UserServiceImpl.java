package com.hugo.blog.service.impl;

import com.hugo.blog.mapper.ArticleMapper;
import com.hugo.blog.mapper.UserMapper;
import com.hugo.blog.model.dto.ArticleDTO;
import com.hugo.blog.model.dto.SportlistDTO;
import com.hugo.blog.model.dto.UsersDTO;
import com.hugo.blog.model.map.ArticleAndDetailMap;
import com.hugo.blog.model.po.ArticleDetailPO;
import com.hugo.blog.model.po.ArticlePO;
import com.hugo.blog.model.po.SportlistPO;
import com.hugo.blog.model.po.UsersPO;
import com.hugo.blog.service.ArticleService;
import com.hugo.blog.service.UserService;
import com.hugo.blog.settings.ArticleSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor // 等同構造注入
public class UserServiceImpl implements UserService {


    private final UserMapper userMapper;

    // user登入
    @Override
    public boolean userLogin(UsersDTO usersDTO) {
        log.info(usersDTO.getUsername()+" 執行userLogin.service");
        return userMapper.checkNameAndPassword(usersDTO.getUsername(), usersDTO.getPassword());
    }

    // 註冊user
    @Override
    public boolean addUser(UsersDTO usersDTO) {
        log.info(usersDTO.getUsername()+" 執行addUser.service");
        UsersPO usersPO = new UsersPO();
        usersPO.setUsername(usersDTO.getUsername());
        usersPO.setPassword(usersDTO.getPassword());
        usersPO.setEmail(usersDTO.getEmail());
        usersPO.setSex(usersDTO.getSex());
        usersPO.setAge(usersDTO.getAge());
        return userMapper.addUsers(usersPO);
    }

    // 使用username查詢userdata
    @Override
    public UsersDTO queryByUsername(String username) {
        log.info(username+" 執行queryByUsername.service");
        UsersPO usersPO = userMapper.gitUserData(username);
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUser_id(usersPO.getUser_id());
        usersDTO.setUsername(usersPO.getUsername());
        usersDTO.setPassword(usersPO.getPassword());
        usersDTO.setEmail(usersPO.getEmail());
        usersDTO.setSex(usersPO.getSex());
        usersDTO.setAge(usersPO.getAge());
        return usersDTO;
    }

    // 使用username和信箱查詢password
    @Override
    public boolean queryByPwd(UsersDTO usersDTO) {
        log.info(usersDTO.getUsername()+" 執行queryByPassword.service");
        UsersPO usersPO = new UsersPO();
        usersPO.setUsername(usersDTO.getUsername());
        usersPO.setEmail(usersDTO.getEmail());
        return userMapper.queryByPasswor(usersPO);
    }

    // 檢查username是否重複
    @Override
    public boolean checkUser(String username) {
        log.info(username+" 執行queryByPassword.service");
        return userMapper.checkuser(username);
    }

    // 修改userdata
    @Override
    public Boolean editUserData(UsersDTO usersDTO) {
        log.info(usersDTO.getUsername()+" 執行editUserData.service");
        UsersPO usersPO = new UsersPO();
        usersPO.setUser_id(usersDTO.getUser_id());
        usersPO.setPassword(usersDTO.getPassword());
        usersPO.setEmail(usersDTO.getEmail());
        usersPO.setSex(usersDTO.getSex());
        usersPO.setAge(usersDTO.getAge());
        return userMapper.editUserData(usersPO);
    }


}

