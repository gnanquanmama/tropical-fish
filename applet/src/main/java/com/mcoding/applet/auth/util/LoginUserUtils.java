package com.mcoding.applet.auth.util;


import com.mcoding.applet.auth.bo.RegisterBo;

/**
 * @author wzt on 2019/11/13.
 * @version 1.0
 */
public class LoginUserUtils {

    private static ThreadLocal<RegisterBo> threadLocal = new ThreadLocal<>();

    public static void binding(RegisterBo registerBo) {
        threadLocal.set(registerBo);
    }

    public static RegisterBo getRegisterBo() {
        return threadLocal.get();
    }

    public static Integer getUserId() {
        return getRegisterBo().getUserId();
    }

    public static String getToken() {
        return getRegisterBo().getToken();
    }

    public static String getSessionKey() {
        return getRegisterBo().getSessionKey();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
