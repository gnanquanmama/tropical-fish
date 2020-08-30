package com.mcoding.modular.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mcoding.base.common.util.Assert;
import com.mcoding.base.core.rest.ResponseResult;
import com.mcoding.modular.auth.support.LoginRequired;
import com.mcoding.modular.auth.support.LoginUserUtils;
import com.mcoding.modular.system.user.entity.SysUser;
import com.mcoding.modular.system.user.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.javasimon.aop.Monitored;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author wzt on 2020/6/19.
 * @version 1.0
 */
@Api(tags = "业务-鉴权服务")
@RestController
public class AppAuthController {

    @Resource
    private SysUserService sysUserService;

    @Monitored
    @ApiOperation(value = "登录")
    @PostMapping("/service/auth/login")
    public ResponseResult<SysUser> login(@ApiParam("名称") @RequestParam(defaultValue = "admin") String name,
                                         @ApiParam("密码") @RequestParam(defaultValue = "123456") String password) {

        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysUser::getName, name)
                .eq(SysUser::getPassword, password);

        SysUser currentUser = this.sysUserService.getOne(queryWrapper);

        Assert.isNotNull(currentUser, String.format("用户%s不存在", name));

        LoginUserUtils.markAsLogin(currentUser);

        return ResponseResult.success(currentUser);
    }

    @Monitored
    @ApiOperation(value = "whoAmI")
    @GetMapping("/service/auth/whoAmI")
    public ResponseResult<SysUser> whoAmI(@ApiIgnore @LoginRequired SysUser sysUser) {

        return ResponseResult.success(sysUser);
    }


}