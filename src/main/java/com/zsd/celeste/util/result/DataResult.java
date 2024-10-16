package com.zsd.celeste.util.result;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataResult extends Result{
    private Object data;
    protected DataResult(boolean b,String msg,Object data) {
        super(b,msg);
        this.data = data;
    }
    protected DataResult(boolean b,Object data) {
        super(b);
        this.data = data;
    }


    static public DataResult ok(Object data) {
        return new DataResult(true,data);
    }
    static public Result judge(boolean b,Object data) {
        return b ? ok(data) : error();
    }
}
