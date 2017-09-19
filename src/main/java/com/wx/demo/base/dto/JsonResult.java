package com.wx.demo.base.dto;

import java.io.Serializable;

public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = -415196579462215370L;

    public static final int SUCCESS = 1;
    public static final int ERROR = 0;

    private int code;

    private String msg;

    private T data;

    public JsonResult() {
        super();
    }

    public JsonResult(int code,String msg,T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(T data, String message) {
        this.code = 1;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(T data) {
        this.code = 1;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
