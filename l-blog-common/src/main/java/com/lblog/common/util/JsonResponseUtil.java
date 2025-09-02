package com.lblog.common.util;

public class JsonResponseUtil <T>{
    private Integer code;
    private String msg;
    private T data;

    public JsonResponseUtil(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public JsonResponseUtil(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> JsonResponseUtil<T> success(){
        return new JsonResponseUtil<>(1, "成功！");
    }

    public static <T> JsonResponseUtil<T> success(T data){
        return new JsonResponseUtil<>(1, "成功！", data);
    }

    public static <T> JsonResponseUtil<T> error(){
        return new JsonResponseUtil<>(2, "失败！");
    }

    public static <T> JsonResponseUtil<T> error(T data){
        return new JsonResponseUtil<>(2, "失败！", data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
