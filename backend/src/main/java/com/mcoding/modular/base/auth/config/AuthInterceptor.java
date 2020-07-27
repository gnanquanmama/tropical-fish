package com.mcoding.modular.base.auth.config;

import com.alibaba.fastjson.JSON;
import com.mcoding.base.core.rest.ResponseResult;
import com.mcoding.base.core.rest.ResponseCode;
import com.mcoding.base.user.entity.BaseUser;
import lombok.extern.slf4j.Slf4j;
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

        BaseUser loginUser = (BaseUser) request.getSession().getAttribute("currentUser");

        if (loginUser == null) {
            this.write(response, ResponseResult.fail(ResponseCode.UNAUTHORIZED, "请先登录"));
            return false;
        }

        return true;
    }

    private void write(HttpServletResponse response, ResponseResult<String> responseResult) throws Exception {
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(responseResult));
        writer.flush();
        writer.close();
    }

}
