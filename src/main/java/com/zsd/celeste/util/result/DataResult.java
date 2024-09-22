package com.zsd.celeste.util.result;



public class DataResult extends Result{
    private Object data;

    protected DataResult(String msg,Object data) {
        super(msg);
        this.data = data;
    }
    protected DataResult(boolean b,Object data) {
        super(b);
        this.data = data;
    }

    public DataResult setData(Object data) {
        this.data = data;
        return this;
    }
    public Object getData() {
        return data;
    }



    static public DataResult ok(Object data) {
        return new DataResult(true,data);
    }
    static public Result judge(boolean b,Object data) {
        return b ? ok(data) : error();
    }
}
