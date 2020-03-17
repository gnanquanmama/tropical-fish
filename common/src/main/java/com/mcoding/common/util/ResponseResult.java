package com.mcoding.common.util;


import lombok.Data;

import java.io.Serializable;

/**
 * @author wzt
 */
@Data
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    private T data;

    public static ResponseResult<String> success() {
        return success(null);
    }

    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(ResponseCode.Success.getCode());
        responseResult.setMsg(ResponseCode.Success.getMsg());
        responseResult.setData(data);
        return responseResult;
    }

    public static ResponseResult<String> fail(String msg) {
        return fail(ResponseCode.Fail, msg);
    }

    public static <T> ResponseResult<T> fail(ResponseCode responseCode, String msg) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(responseCode.getCode());
        responseResult.setMsg(msg);
        return responseResult;
    }

    public boolean isSuccess() {
        return ResponseCode.Success.getCode().equals(code);
    }

}
