package com.zsd.celeste.util;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Result {
    private String msg;
    private Integer code;
    private Object data;
    static public Result success(Object data){
        return new Result("ok",200,data);
    }
    static public Result fail(Object data){
        return new Result("error",400,data);
    }



    static public Result judge(boolean b){
        return judge(b,null,null);
    }
    static public Result judge(boolean b,Object msg){
        return judge(b,msg,null);
    }
    static public Result judge(boolean b,Object msg,Object errMsg){
        return b?Result.success(msg):Result.fail(errMsg);
    }



    static public Result notNull(Object o){
        return notNull(o,null);
    }
    static public Result notNull(Object o,Object errMsg){
        return judge(!Objects.isNull(o),o,errMsg);
    }
    static public Result notEmpty(Collection<?> o,Object errMsg) {
        return judge(!o.isEmpty(),o,errMsg);
    }

    static public Result page(IPage<?> p){
        return judge(!p.getRecords().isEmpty(),p,"index over");
    }

}
