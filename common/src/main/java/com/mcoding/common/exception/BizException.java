package com.mcoding.common.exception;


/**
 * 业务异常
 *
 * @author wzt on 2020/3/9.
 * @version 1.0
 */
public class BizException extends RuntimeException {


    public BizException() {
        super();
    }

    public BizException(String message, Throwable cause, boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(String message) {
        super(message);
    }


    public BizException(String message, Object data) {
        super(message);
    }

    public BizException(Throwable cause) {
        super(cause);
    }


}
