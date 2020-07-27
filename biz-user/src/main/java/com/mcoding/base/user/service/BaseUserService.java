package com.mcoding.base.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mcoding.base.user.entity.BaseUser;

/**
 * <p>
 * 基础用户 服务类
 * </p>
 *
 * @author wzt
 * @since 2020-06-21
 */
public interface BaseUserService extends IService<BaseUser> {

    /**
     * 通过openID获取用户对象
     *
     * @param openId
     * @return
     */
    BaseUser getUserByOpenId(String openId);

}
