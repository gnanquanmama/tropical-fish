package com.mcoding.applet.auth.base.config;

import com.alibaba.fastjson.JSON;
import com.mcoding.applet.auth.business.RegisterBo;
import com.mcoding.applet.auth.service.WechatAuthService;
import com.mcoding.applet.auth.util.LoginUserUtils;
import com.mcoding.base.core.rest.ResponseCode;
import com.mcoding.base.core.rest.ResponseResult;
import com.mcoding.base.core.spring.SpringContextHolder;
import groovy.util.logging.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author wzt on 2019/11/13.
 * @version 1.0
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            this.write(response, ResponseResult.fail(ResponseCode.UNAUTHORIZED, "HTTP HEADER 携带的token不能为空"));
            return false;
        }

        WechatAuthService wechatAuthService = SpringContextHolder.getOneBean(WechatAuthService.class);
        RegisterBo registerBo = wechatAuthService.getRegisterByToken(token);
        if (registerBo == null) {
            this.write(response, ResponseResult.fail(ResponseCode.UNAUTHORIZED, "token已失效,请重新授权登录认证"));
            return false;
        }

        // 绑定当前线程对应的用户
        LoginUserUtils.binding(registerBo);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 情况当前线程的绑定用户信息
        LoginUserUtils.remove();
    }

    private void write(HttpServletResponse response, ResponseResult<String> responseResult) throws Exception {
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(responseResult));
        writer.flush();
        writer.close();
    }

}
