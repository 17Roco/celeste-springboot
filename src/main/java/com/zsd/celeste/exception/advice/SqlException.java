package com.zsd.celeste.exception.advice;

import com.zsd.celeste.util.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@ControllerAdvice
public class SqlException {


    /**
     * DuplicateKeyException重复键
     * DataIntegrityViolationException数据完整性违规
     * NonTransientDataAccessException非瞬态数据访问
     * DataAccessException数据访问
     * NestedRuntimeException嵌套运行时
     * */
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    Result sqlException(SQLException e) {
        return Result.error(e.getMessage());
    }
}
