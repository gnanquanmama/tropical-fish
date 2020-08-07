package com.mcoding.applet.auth.service;


import com.mcoding.applet.auth.business.RegisterBo;
import com.mcoding.applet.auth.business.UserInfoBo;
import com.mcoding.applet.auth.dto.CreateUserDto;
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
	 * 登录
	 *
	 * @param persistenceUser
	 * @param userInfoBo
	 * @param token
	 * @return
	 */
	RegisterBo login(BaseUser persistenceUser, UserInfoBo userInfoBo, String token);

    /**
     * 根据openId登录
     *
     * @param openId
     */
    RegisterBo login(String openId, String token);

	/**
	 * 查询当前用户token
	 *
	 * @param token
	 * @return
	 */
	RegisterBo getUserToken(String token);

    /**
     * 失效用户token
     *
     * @param token
     */
    void invalidUserToken(String token);

}
