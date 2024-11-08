package com.zsd.celeste.util.result;

import com.zsd.celeste.enums.ResultCode;
import lombok.Data;
import org.springframework.cglib.core.internal.Function;

import java.util.HashMap;

@Data
public class Result {
    private int code;
    private String msg;
    private Object data;

    public Result(ResultCode code, String msg, Object data) {
        this.code = code.getCode();
        this.msg = msg == null ? code.getText() : msg;
        this.data = data;
    }


    static public Result ok(Object data) {
        return new Result(ResultCode.OK, null, data);
    }
    static public Result error(Object data) {
        return new Result(ResultCode.ERROR, null, data);
    }
    static public Result judge(boolean b){
        return b? ok(null) : error(null);
    }

    static public Result map(String key, Object value){
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        return new Result(ResultCode.OK, null,map);
    }
    static public Result map(Function<MapResult, Object> function){
        MapResult mapResult = new MapResult();
        function.apply(mapResult);
        return new Result(ResultCode.OK, null,mapResult);
    }
}

