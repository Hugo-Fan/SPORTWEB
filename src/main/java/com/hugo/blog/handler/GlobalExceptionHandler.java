package com.hugo.blog.handler;


import com.hugo.blog.handler.exp.IdTypeException;
import com.hugo.blog.model.dto.UsersDTO;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

//    //處理JSR303
    @ExceptionHandler({BindException.class})
    public String handlerBindException(BindException bindException, Model model){
        BindingResult bindingResult = bindException.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        model.addAttribute("errors",fieldErrors);
        return "blog/error/bind";

    }

    @ExceptionHandler({IdTypeException.class})
    public String handleIdTypeException(IdTypeException idTypeException,Model model){
        model.addAttribute("msg",idTypeException.getMessage());
        return "blog/error/error";
    }

//    @ExceptionHandler({RuntimeException.class})
//    public String handleDefalutException(Exception e,Model model){
//        model.addAttribute("msg","請稍後重試!");
//        return "/blog/error/error";
//    }

}
