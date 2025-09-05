package com.lblog.common.exception;

public class ReturnException extends RuntimeException {
    private Integer code;
    private String msg;

    public ReturnException(String msg){
        super(msg);
        this.code = 500;
        this.msg = msg;
    }

    public ReturnException(Integer code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ReturnException(String msg, Throwable cause){
        super(msg, cause);
        this.code = 500;
        this.msg = msg;
    }

    public ReturnException(Integer code, String msg, Throwable cause){
        super(msg, cause);
        this.code = code;
        this.msg = msg;
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
}
