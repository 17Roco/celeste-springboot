package com.zsd.celeste.util.result;

import lombok.Getter;
import java.util.HashMap;

@Getter
public class StreamResult extends Result {

    final private HashMap<String,Object> data;

    protected StreamResult() {
        super(true);
        data = new HashMap<>();
    }
    public StreamResult put(String  k, Object v) {
        data.put(k,v);
        return this;
    }

    static public StreamResult put() {return new StreamResult();}
}
