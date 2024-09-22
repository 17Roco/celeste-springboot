package com.zsd.celeste.util.result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.Objects;

@Getter
public class Result {
    private String msg;
    protected Result(String msg) {
        this.msg = msg;
    }
    protected Result(boolean b) {
        this.msg = b ? "ok" : "error";
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    static public Result ok(){
        return new Result(true);
    }
    static public Result error(){
        return new Result(false);
    }
    static public Result error(String msg){
        return new Result(msg);
    }
    static public Result judge(boolean b){
        return b ? ok() : error();
    }
}
