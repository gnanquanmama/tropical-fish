package com.mcoding.applet.auth.service;


import com.mcoding.applet.auth.bo.RegisterBo;
import com.mcoding.applet.auth.bo.UserInfoBo;
import com.mcoding.applet.auth.controller.dto.CreateUserDto;
import com.mcoding.base.user.entity.BaseUser;

/**
 * @author wzt on 2020/3/25.
 * @version 1.0
 */
public interface WechatAuthService {

    /**
     * 创建用户
     *
     * @param createUserDto
     * @param userInfoBo
     * @param token
     */
    RegisterBo register(BaseUser currentUser, CreateUserDto createUserDto, UserInfoBo userInfoBo, String token);

    /**
     * 根据openId登录
     *
     * @param openId
     */
    RegisterBo login(String openId, String token);


    /**
     * 根据token查询当前用户绑定信息
     *
     * @param token
     * @return
     */
    RegisterBo getRegisterByToken(String token);

    /**
     * 门店是否绑定
     *
     * @param userId
     * @return
     */
    int isStoreBinding(Integer userId);


    /**
     * 绑定门店
     *
     * @param baseUser
     * @param token
     * @param wxAccessToken
     * @return
     */
    RegisterBo bindingStore(BaseUser baseUser, String token, String wxAccessToken);


    /**
     * 失效用户token
     *
     * @param token
     */
    void invalidUserToken(String token);

    /**
     * 解绑门店
     *
     * @param userId
     * @return
     */
    void unBindStore(int userId);

    /**
     * 登录
     *
     * @param persistenceUser
     * @param userInfoBo
     * @param token
     * @return
     */
    RegisterBo login(BaseUser persistenceUser, UserInfoBo userInfoBo, String token);

}
