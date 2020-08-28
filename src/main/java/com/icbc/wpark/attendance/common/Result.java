package com.icbc.wpark.attendance.common;

public class Result<T> {
    int code;
    T data;
    String msg;

    public Result() {
    }

    public Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> Result<T> ok(T data, String msg) {
        return new Result<T>(200, data, msg);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<T>(-1,null, msg) ;}

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }


}
