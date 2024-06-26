package com.zsd.celeste.exception;

import com.zsd.celeste.util.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class IndexException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    Result index(Exception e){
        System.out.println(e.getMessage());
//        e.printStackTrace();
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    Result accessDenied(){
        return Result.fail("权限不足");
    }


    // MethodArgumentTypeMismatchException

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    Result MethodArgumentTypeMismatch(){
        return Result.fail("Argument Type Mismatch");
    }
}
