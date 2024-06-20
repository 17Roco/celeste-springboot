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
        return fail("error",data);
    }
    static public Result fail(String  msg){
        return fail(msg,null);
    }
    static public Result fail(String msg,Object data){
        return new Result(msg,400,data);
    }


    static public Result judge(boolean b,Object msg){
        return judge(b,msg,null);
    }
    static public Result judge(boolean b,Object msg,Object errMsg){
        return b?Result.success(msg):Result.fail(errMsg);
    }



    static public Result notNull(Object o){
        return notNull(o,"null");
    }
    static public Result notNull(Object o,Object errMsg){
        return judge(!Objects.isNull(o),o,errMsg);
    }
    static public Result notEmpty(Collection<?> o,Object errMsg) {
        return judge(!o.isEmpty(),o,errMsg);
    }


    ///

    static public Result judge(boolean res){
        return res ? success(null) : fail(null);
    }
    static public Result success(boolean res,String errMag){
        return res ? success(null) : fail(errMag);
    }

    static public Result page(IPage<?> p){
        if (p.getRecords().isEmpty())
            return fail("index over",p);
        return success(p);
    }

}
