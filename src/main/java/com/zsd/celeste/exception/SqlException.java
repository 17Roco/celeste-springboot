package com.zsd.celeste.exception;

import com.zsd.celeste.util.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@ControllerAdvice
public class SqlException {

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    Result sqlException(SQLException e) {
        return Result.error(e.getMessage());
    }
}
