package com.hugo.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.hugo.blog.handler.exp.IdTypeException;
import com.hugo.blog.model.dto.UsersDTO;
import com.hugo.blog.model.param.UserParam;
import com.hugo.blog.model.vo.UsersVO;
import com.hugo.blog.service.MailService;
import com.hugo.blog.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
//@RestController
public class UserController {
    @Autowired
    private  UserService userService;

    @Autowired
    private MailService mailService;

    private final String salt = "hugoISport";

    //登入帳號
    @PostMapping("/user/userLogin")
    public String userLogin(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam(defaultValue="/index") String inUrl, HttpSession session, Model model) {
        // 應用service

        // 用 MD5 加密
        String hashPd = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes());
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUsername(username);
        usersDTO.setPassword(hashPd);
        boolean logintrue = userService.userLogin(usersDTO);
        if (logintrue == true) {
            UsersDTO setUserData = userService.queryByUsername(username);
            session.setAttribute("userdata", setUserData);
            log.info(username+" 帳號登入成功");
            return "redirect:" + inUrl;
        } else {
            model.addAttribute("loginerror","登入錯誤請檢查帳號或密碼");
            log.info(username+" 帳號登入失敗");
            return "blog/userLogin";
        }
    }

    //查詢密碼
    @PostMapping("/user/forgetpw")
    public String forgetPd(@RequestParam String username,
                           @RequestParam String mail,
                           Model model) {
        // 應用service
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUsername(username);
        usersDTO.setEmail(mail);
        boolean loginTrue = userService.queryByPwd(usersDTO);
        if (loginTrue == true) {
            UsersDTO usersData = userService.queryByUsername(username);

            // 寄送密碼給會員
            String subject = "關於您忘記密碼";
            String content = "您的密碼: " + usersData.getPassword();
            mailService.sendPlainText(mail, subject, content);

            model.addAttribute("pw","密碼已寄信至mail");
            log.info(username+" 查詢密碼成功");
            return "blog/forgetpasword";
        } else {
            model.addAttribute("forgeterror","請確認姓名和電子郵件是否輸入正確");
            log.info(username+" 查詢密碼失敗");
            return "blog/forgetpasword";
        }
    }

    //登出帳號
    @PostMapping("/user/signOut")
    public String SignOutUser(HttpSession session ,@RequestParam(defaultValue="/index")  String outUrl){
        String username =((UsersDTO) session.getAttribute("userdata")).getUsername();
        session.removeAttribute("userdata");
        log.info(username+" 帳號登出成功");
        return "redirect:" + outUrl;
    }

    //註冊新帳號 @RequestParam String username , String password , String email, String sex, Integer age
    @PostMapping("/user/addUser")
    public String addUser(@Validated(UserParam.AddUser.class) UserParam param) {
        // 應用service
        boolean checkUsernameRepeat = userService.checkUser(param.getUsername());
        if (checkUsernameRepeat == true ) {
            throw new IdTypeException("帳號重複");
        }
        // 用 MD5 加密
        String hashPd = DigestUtils.md5DigestAsHex((salt+param.getPassword()+salt).getBytes());

        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUsername(param.getUsername());
        usersDTO.setPassword(hashPd);
        usersDTO.setEmail(param.getEmail());
        usersDTO.setSex(param.getSex());
        usersDTO.setAge(param.getAge());
        boolean add = userService.addUser(usersDTO);

        if (add == true) {
            log.info(usersDTO.getUsername()+" 帳號新建成功");
            return "redirect:/view/userlogin";
        } else {
            log.info(usersDTO.getUsername()+" 帳號新建失敗");
            return "blog/error/error";
        }
    }

    //查詢User資料
    @GetMapping("/user/UserDataGet")
    public String queryByUserId(@RequestParam String username, Model model,HttpSession session) {
        if(session.getAttribute("userdata" )!=null){
            UsersDTO UserData = userService.queryByUsername(username);
            UsersVO usersVO = BeanUtil.copyProperties(UserData,UsersVO.class);
            model.addAttribute("userdata", usersVO);
            log.info(username+" user資料查詢成功");
            return "blog/editUser";
        } else {
            log.info(username+" user資料查詢失敗");
            return "blog/error/error";
        }
    }

    //更新帳號資料
    @PostMapping("/user/editUser")
    public String editUserData(@Validated(UserParam.EditUser.class) UserParam param) {
        // 用 MD5 加密
        String hashPd = DigestUtils.md5DigestAsHex((salt+param.getPassword()+salt).getBytes());

        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUser_id(param.getUser_id());
        usersDTO.setPassword(hashPd);
        usersDTO.setEmail(param.getEmail());
        usersDTO.setSex(param.getSex());
        usersDTO.setAge(param.getAge());
        Boolean edit = userService.editUserData(usersDTO);
        if(edit == true){
            log.info(usersDTO.getUsername()+" user資料更新成功");
            return "redirect:/index";
        }else {
            log.info(usersDTO.getUsername()+" user資料更新失敗");
            return "blog/editUser";
        }
    }
}

