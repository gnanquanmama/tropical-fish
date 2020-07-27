package com.mcoding.applet.auth.controller;


import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.mcoding.applet.auth.business.RegisterBo;
import com.mcoding.applet.auth.business.UserInfoBo;
import com.mcoding.applet.auth.service.BaseUserTokenService;
import com.mcoding.applet.auth.service.WechatAuthService;
import com.mcoding.applet.auth.service.WechatService;
import com.mcoding.applet.auth.util.LoginUserUtils;
import com.mcoding.applet.auth.controller.dto.CreateUserDto;
import com.mcoding.applet.auth.controller.dto.PhoneNumberDto;
import com.mcoding.applet.auth.controller.dto.RegisterDto;
import com.mcoding.base.common.util.bean.BeanMapperUtils;
import com.mcoding.base.common.util.wechat.WXBizDataCrypt;
import com.mcoding.base.common.util.wechat.WxUserInfo;
import com.mcoding.base.core.doc.Process;
import com.mcoding.base.core.rest.ResponseCode;
import com.mcoding.base.core.rest.ResponseResult;
import com.mcoding.base.user.entity.BaseUser;
import com.mcoding.base.user.service.BaseUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@Api(tags = "业务基础-授权服务")
@RestController
public class UserAuthController {


    @Resource
    private WechatService wechatService;

    @Resource
    private WechatAuthService wechatAuthService;

    @Resource
    private BaseUserService baseUserService;

    @Resource
    private BaseUserTokenService baseUserTokenService;

    @ApiOperation("小程序获取用户手机号码")
    @GetMapping("/service/app/wxuser/getPhoneNumber")
    public ResponseResult<PhoneNumberDto> getPhoneNumber(@ApiParam("加密数据") @RequestParam String encryptedData,
                                                         @ApiParam("加密算法的初始向量") @RequestParam String iv) {
        String sessionKey = LoginUserUtils.getSessionKey();

        log.info("EVENT=小程序获取用户手机号码|encryptedData={}|iv={}|sessionKey={}", encryptedData, iv, sessionKey);
        WxUserInfo userInfo = WXBizDataCrypt.decrypt(encryptedData, sessionKey, iv);

        String phoneNumber = userInfo.getPhoneNumber();
        return ResponseResult.success(new PhoneNumberDto(phoneNumber));
    }


    @Process(comment = "小程序用户注册")
    @ApiOperation("小程序用户注册")
    @PostMapping("/service/app/wxuser/register")
    public ResponseResult<RegisterDto> register(@Valid @RequestBody CreateUserDto createUserDto) {

        log.info("EVENT=小程序用户注册|USER_INFO={}", JSON.toJSONString(createUserDto));

        String jsCode = createUserDto.getJsCode();
        UserInfoBo userInfoBo = this.wechatService.getUserInfoByCode(jsCode);
        String openId = userInfoBo.getOpenId();

        BaseUser persistenceUser = this.baseUserService.getUserByOpenId(openId);
        if (persistenceUser != null) {

            String authToken = this.baseUserTokenService.getToken(persistenceUser.getId());
            // 当前用户已有授权token，则失效该token
            if (StringUtils.isNotEmpty(authToken)) {
                this.wechatAuthService.invalidUserToken(authToken);
            }
        }

        String newToken = IdUtil.simpleUUID();
        RegisterBo registerBo = this.wechatAuthService.register(persistenceUser, createUserDto, userInfoBo, newToken);

        // 记录当前用户Token
        this.baseUserTokenService.saveNewToken(registerBo.getUserId(), newToken);

        return ResponseResult.success(BeanMapperUtils.map(registerBo, RegisterDto.class));
    }


    @Process(comment = "小程序用户登录")
    @ApiOperation("小程序用户登录")
    @PostMapping("/service/app/wxuser/loginByJsCode")
    public ResponseResult<RegisterDto> loginByJsCode(@ApiParam("JsCode") @RequestParam String jsCode) {

        log.info("EVENT=小程序用户登录|JS_CODE={}", jsCode);

        UserInfoBo userInfoBo = this.wechatService.getUserInfoByCode(jsCode);
        String openId = userInfoBo.getOpenId();

        BaseUser persistenceUser = this.baseUserService.getUserByOpenId(openId);
        if (persistenceUser == null) {
            return ResponseResult.fail(ResponseCode.User_Not_Found, "用户还未注册，请先注册");
        }

        String authToken = this.baseUserTokenService.getToken(persistenceUser.getId());

        // 当前用户已有授权token，则失效该token
        if (StringUtils.isNotEmpty(authToken)) {
            this.wechatAuthService.invalidUserToken(authToken);
        }

        String newToken = IdUtil.simpleUUID();
        RegisterBo registerBo = this.wechatAuthService.login(persistenceUser, userInfoBo, newToken);

        // 记录当前用户Token
        this.baseUserTokenService.saveNewToken(registerBo.getUserId(), newToken);

        return ResponseResult.success(BeanMapperUtils.map(registerBo, RegisterDto.class));
    }

    @ApiOperation("获取测试token[后端测试使用]")
    @PostMapping("/service/app/wxuser/login")
    public ResponseResult<RegisterDto> login(@RequestParam("openId") String openId) {
        String token = IdUtil.simpleUUID();
        RegisterBo registerBo = this.wechatAuthService.login(openId, token);

        return ResponseResult.success(BeanMapperUtils.map(registerBo, RegisterDto.class));
    }

}
