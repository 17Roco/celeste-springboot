package com.zsd.celeste.util.result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.*;

import java.util.Collection;
import java.util.Objects;

@Getter
public class Result {
    @Setter
    private String msg;
    private Integer code;

    protected Result(boolean b,String msg) {
        this.code = b ? 200 : 400;
        this.msg = msg;
    }
    protected Result(boolean b) {
        this.code = b ? 200 : 400;
        this.msg = b ? "ok" : "error";
    }

    public void toOk(){
        code = 200;
    }
    public void toError(){
        code = 400;
    }

    static public Result ok(){
        return new Result(true);
    }
    static public Result error(){
        return new Result(false);
    }
    static public Result error(String msg){
        return new Result(false,msg);
    }
    static public Result judge(boolean b){
        return b ? ok() : error();
    }
}
