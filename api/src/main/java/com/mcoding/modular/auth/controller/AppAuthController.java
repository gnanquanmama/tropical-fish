package com.mcoding.modular.auth.controller;

import com.mcoding.base.rest.ResponseResult;
import com.mcoding.modular.auth.entity.LoginUser;
import com.mcoding.modular.auth.util.LoginUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.javasimon.aop.Monitored;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzt on 2020/6/19.
 * @version 1.0
 */
@Api(tags = "基础用户服务")
@RestController
public class AppAuthController {

    @Monitored
    @ApiOperation(value = "注册")
    @PostMapping("/auth/register")
    public ResponseResult<String> register(@Validated @RequestBody LoginUser loginUser) {

        LoginUserUtils.markAsLogin(loginUser);

        return ResponseResult.success();
    }
}