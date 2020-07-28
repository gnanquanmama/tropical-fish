package com.mcoding.applet.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mcoding.applet.auth.business.RegisterBo;
import com.mcoding.applet.auth.business.UserInfoBo;
import com.mcoding.applet.auth.controller.dto.CreateUserDto;
import com.mcoding.applet.auth.service.WechatAuthService;
import com.mcoding.base.common.util.bean.BeanMapperUtils;
import com.mcoding.base.common.util.constant.SysConstants;
import com.mcoding.base.core.cache.RCacheEvict;
import com.mcoding.base.core.cache.RCacheable;
import com.mcoding.base.core.doc.Phase;
import com.mcoding.base.user.entity.BaseUser;
import com.mcoding.base.user.service.BaseUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 小程序用户服务
 *
 * @author wzt on 2020/3/25.
 * @version 1.0
 */
@Service
public class WechatAuthServiceImpl implements WechatAuthService {

	@Resource
	private BaseUserService baseUserService;

	@Phase(comment = "注册用户到DMT系统")
	@RCacheable(key = "dmt::miniprogram::token", secKey = "#token", ttl = 1, timeUnit = TimeUnit.DAYS)
	@Override
	public RegisterBo register(BaseUser persistenceUser, CreateUserDto createUserDto, UserInfoBo userInfoBo, String token) {
		RegisterBo registerBo;
		if (persistenceUser == null) {
			// 当前用户还未入库，则先入库
			BaseUser baseUser = BeanMapperUtils.map(createUserDto, BaseUser.class);

			baseUser.setOpenId(userInfoBo.getOpenId());
			baseUser.setUnionId(userInfoBo.getUnionid());
			baseUser.setCreateTime(new Date());
			baseUserService.save(baseUser);

			registerBo = BeanMapperUtils.map(baseUser, RegisterBo.class);
			registerBo.setUserId(baseUser.getId());
			registerBo.setSessionKey(userInfoBo.getSessionKey());
			registerBo.setToken(token);
		} else {
			registerBo = BeanMapperUtils.map(persistenceUser, RegisterBo.class);
			registerBo.setUserId(persistenceUser.getId());
			registerBo.setSessionKey(userInfoBo.getSessionKey());
			registerBo.setToken(token);
		}

		return registerBo;
	}

	@Phase(comment = "用户登录DMT系统")
	@RCacheable(key = "dmt::miniprogram::token", secKey = "#token", ttl = 1, timeUnit = TimeUnit.DAYS)
	@Override
	public RegisterBo login(BaseUser persistenceUser, UserInfoBo userInfoBo, String token) {

		RegisterBo registerBo = BeanMapperUtils.map(persistenceUser, RegisterBo.class);
		registerBo.setUserId(persistenceUser.getId());
		registerBo.setSessionKey(userInfoBo.getSessionKey());
		registerBo.setToken(token);

		return registerBo;
	}

	@Phase(comment = "用户登录DMT系统")
	@RCacheable(key = "dmt::miniprogram::token", secKey = "#token", ttl = 2, timeUnit = TimeUnit.HOURS)
	@Override
	public RegisterBo login(String openId, String token) {
		QueryWrapper<BaseUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(BaseUser::getOpenId, openId);
		BaseUser currentUser = baseUserService.getOne(queryWrapper);

		RegisterBo registerBo = BeanMapperUtils.map(currentUser, RegisterBo.class);
		registerBo.setUserId(currentUser.getId());
		registerBo.setToken(token);

		return registerBo;
	}

	@RCacheable(key = "dmt::miniprogram::token", secKey = "#token")
	@Override
	public RegisterBo getUserToken(String token) {
		return null;
	}

	@Override
	public int isStoreBinding(Integer userId) {
		BaseUser baseUser = this.baseUserService.getById(userId);
		String storeId = baseUser.getStoreId();
		return StringUtils.isNotEmpty(storeId) ? SysConstants.YES : SysConstants.NO;
	}

	@Override
	public RegisterBo bindingStore(BaseUser baseUser, String token, String wxAccessToken) {
		this.baseUserService.updateById(baseUser);

		BaseUser currentUser = this.baseUserService.getById(baseUser.getId());
		RegisterBo registerBo = BeanMapperUtils.map(currentUser, RegisterBo.class);
		registerBo.setUserId(currentUser.getId());
		registerBo.setToken(token);
		registerBo.setSessionKey(wxAccessToken);

		return registerBo;
	}

	@Phase(comment = "失效用户token")
	@RCacheEvict(key = "dmt::miniprogram::token", secKey = "#token")
	@Override
	public void invalidUserToken(String token) {

	}

	@Override
	public void unBindStore(int userId) {
		BaseUser baseUser = new BaseUser();
		baseUser.setId(userId);
		baseUser.setStoreId("");
		baseUser.setStoreCode("");
		baseUser.setStoreName("");
		this.baseUserService.updateById(baseUser);

	}
}
