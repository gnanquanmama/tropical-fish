package com.mcoding.modular.base.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mcoding.base.core.rest.ResponseResult;
import com.mcoding.base.common.util.Assert;
import com.mcoding.modular.base.auth.util.LoginUserUtils;
import com.mcoding.base.user.entity.BaseUser;
import com.mcoding.base.user.service.BaseUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.javasimon.aop.Monitored;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wzt on 2020/6/19.
 * @version 1.0
 */
@Api(tags = "业务-鉴权服务")
@RestController
public class AppAuthController {

    @Resource
    private BaseUserService baseUserService;

    @Monitored
    @ApiOperation(value = "登录")
    @PostMapping("/service/auth/login")
    public ResponseResult<BaseUser> login(@RequestParam String mobilePhone) {

        QueryWrapper<BaseUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseUser::getMobileNumber, mobilePhone);

        BaseUser currentUser = this.baseUserService.getOne(queryWrapper);

        Assert.isNotNull(currentUser, String.format("用户%s不存在", mobilePhone));

        LoginUserUtils.markAsLogin(currentUser);

        return ResponseResult.success(currentUser);
    }
}