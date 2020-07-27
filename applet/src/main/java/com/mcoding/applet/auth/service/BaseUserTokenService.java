package com.mcoding.applet.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mcoding.applet.auth.entity.BaseUserToken;

/**
 * <p>
 * 用户授权token 服务类
 * </p>
 *
 * @author wzt
 * @since 2020-04-20
 */
public interface BaseUserTokenService extends IService<BaseUserToken> {


    /**
     * 保存新token
     * @param userId
     * @param newToken
     */
    void saveNewToken(int userId, String newToken);


    /**
     * 查询token
     * @param userId
     * @return
     */
    String getToken(int userId);

}
