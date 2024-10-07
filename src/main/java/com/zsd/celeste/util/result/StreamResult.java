package com.zsd.celeste.util.result;

import lombok.Getter;
import java.util.HashMap;

@Getter
public class StreamResult extends Result {

    final private HashMap<String,Object> data;

    protected StreamResult(boolean b) {
        super(b);
        data = new HashMap<>();
    }

    public StreamResult put(String  k, Object v) {
        data.put(k,v);
        return this;
    }

    static public StreamResult create(boolean b) {return new StreamResult(b);}
    static public StreamResult create(String  k, Object v) {return new StreamResult(true).put(k,v);}
}
