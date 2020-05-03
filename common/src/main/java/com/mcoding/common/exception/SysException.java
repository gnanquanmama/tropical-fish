package com.mcoding.common.exception;

/**
 * 系统异常
 *
 * @author wzt on 2020/3/9.
 * @version 1.0
 */
public class SysException extends RuntimeException {


    public SysException() {
        super();
    }

    public SysException(String message, Throwable cause, boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException(String message) {
        super(message);
    }


    public SysException(String message, Object data) {
        super(message);
    }

    public SysException(Throwable cause) {
        super(cause);
    }


}
