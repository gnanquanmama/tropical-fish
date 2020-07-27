package com.mcoding.modular.auth.support;

import com.mcoding.modular.system.user.entity.SysUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author wzt on 2020/6/19.
 * @version 1.0
 */
public class LoginUserUtils {

    public static SysUser currentUser() {
        HttpSession httpSession = getCurrentSession();
        return (SysUser) httpSession.getAttribute("currentUser");
    }

    public static Long getUserId() {
        return currentUser().getId();
    }


    public static void markAsLogin(SysUser loginUser) {
        HttpSession httpSession = getCurrentSession();
        httpSession.setAttribute("currentUser", loginUser);
    }

    public static void invalidate() {
        HttpSession httpSession = getCurrentSession();
        httpSession.invalidate();
    }

    public static void loginOut() {
        HttpSession httpSession = getCurrentSession();
        httpSession.invalidate();
    }

    private static HttpSession getCurrentSession() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getSession();
    }


}
