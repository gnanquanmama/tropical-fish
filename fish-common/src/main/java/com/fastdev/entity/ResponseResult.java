package com.fastdev.entity;


import com.fastdev.util.ResponseCode;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code; // 响应码：0表示成功，其他表示失败

    private String msg; // 响应描述

    private T data; // 返回数据


    public ResponseResult() {
        super();
    }

    public ResponseResult(String code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public ResponseResult<T> setCode(String code) {
        this.code = code;
        return this;
    }


    public ResponseResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static ResponseResult<String> success() {
        return success("操作成功");
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>().setCode(ResponseCode.Success.getCode())
                .setMsg(ResponseCode.Success.getMsg())
                .setData(data);
    }

    public static ResponseResult<String> fail(ResponseCode code) {
        return fail(code, "");
    }

    public static <T> ResponseResult<T> fail(ResponseCode code, T data) {
        return new ResponseResult<T>().setCode(code.getCode()).setMsg(code.getMsg()).setData(data);
    }

}
