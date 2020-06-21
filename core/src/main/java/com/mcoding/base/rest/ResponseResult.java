package com.mcoding.base.rest;


import com.mcoding.common.util.constant.MdcConstants;
import com.mcoding.common.util.rest.ResponseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.slf4j.MDC;

import java.io.Serializable;

/**
 * @author wzt
 */
@ApiModel("请求返回包装模型")
@Data
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("状态码，200为正常")
    private String code;

    @ApiModelProperty("描述信息")
    private String msg;

    @ApiModelProperty("结果数据")
    private T data;

    @ApiModelProperty("请求追踪ID, ops使用")
    private String traceId;

    public static ResponseResult<String> success() {
        return success(null);
    }

    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(ResponseCode.Success.getCode());
        responseResult.setMsg(ResponseCode.Success.getMsg());
        responseResult.setData(data);
        responseResult.setTraceId(MDC.get(MdcConstants.TRACE_ID));
        return responseResult;
    }

    public static ResponseResult<String> fail(String msg) {
        return fail(ResponseCode.Fail, msg);
    }

    public static <T> ResponseResult<T> fail(ResponseCode responseCode, String msg) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(responseCode.getCode());
        responseResult.setMsg(msg);
        responseResult.setTraceId(MDC.get(MdcConstants.TRACE_ID));
        return responseResult;
    }

    public boolean isSuccess() {
        return ResponseCode.Success.getCode().equals(code);
    }

}
