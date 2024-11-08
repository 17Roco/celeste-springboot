package com.zsd.celeste.util.result;

import java.util.HashMap;

public class MapResult extends HashMap<String, Object> {

    public MapResult() {
        super();
    }
    public MapResult(String key, Object value) {
        super();
        put(key, value);
    }

    public MapResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
