package com.zsd.celeste.exception.advice;

import com.zsd.celeste.exception.exception.UserNotLoginEx;
import com.zsd.celeste.util.result.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class IndexException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    Result index(Exception e){
        System.out.println("error : " + e.getClass() + " - " + e.getMessage());
//        e.printStackTrace();
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    Result accessDenied(){
        return Result.error("权限不足");
    }


    // MethodArgumentTypeMismatchException

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    Result MethodArgumentTypeMismatch(){
        return Result.error("Argument Type Mismatch");
    }

    @ExceptionHandler(UserNotLoginEx.class)
    @ResponseBody
    Result UserNotLogin(){
        return Result.error("需要登录");
    }
}
