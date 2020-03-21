package com.mcoding.base.auth;

import com.alibaba.fastjson.JSON;
import com.mcoding.common.util.ResponseResult;
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
        return true;
    }


    private void write(HttpServletResponse response, String message) throws Exception {
        response.setCharacterEncoding("UTF-8");

        ResponseResult<String> responseResult = ResponseResult.fail(message);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(responseResult));
        writer.flush();
        writer.close();
    }

}
