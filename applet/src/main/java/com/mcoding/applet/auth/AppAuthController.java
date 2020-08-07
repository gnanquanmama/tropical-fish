package com.mcoding.applet.auth;


import com.alibaba.fastjson.JSON;
import com.mcoding.applet.auth.dto.CreateUserDto;
import com.mcoding.applet.auth.dto.RegisterDto;
import com.mcoding.base.core.doc.Process;
import com.mcoding.base.core.rest.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 基础用户
 * </p>
 *
 * @author wzt
 * @since 2020-03-25
 */
@Slf4j
@Api(tags = "业务基础-APP授权服务")
@RestController
public class AppAuthController {

    @Process(comment = "IOS用户注册")
    @ApiOperation("IOS用户注册")
    @PostMapping("/service/app/appuser/register")
    public ResponseResult<RegisterDto> register(@Valid @RequestBody CreateUserDto createUserDto) {

        log.info("EVENT=小程序用户注册|USER_INFO={}", JSON.toJSONString(createUserDto));

        return ResponseResult.success(new RegisterDto());
    }

}
