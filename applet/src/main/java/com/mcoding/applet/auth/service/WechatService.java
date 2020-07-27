package com.mcoding.applet.auth.service;


import com.mcoding.applet.auth.bo.UserInfoBo;

/**
 * @author wzt on 2019/11/12.
 * @version 1.0
 */
public interface WechatService {

    /**
     * 根据jsCode获取用户信息
     *
     * @param code
     * @return
     */
    UserInfoBo getUserInfoByCode(String code);

    /**
     * 获取小程序全局唯一后台接口调用凭据
     *
     * @return
     */
    String getAccessToken();

    /**
     * 失效小程序全局唯一后台接口调用凭据
     *
     * @return
     */
    String evictAccessToken();


    /**
     * 获取小程序二维码
     *
     * @param accessToken
     * @param page
     * @param scene
     * @param width
     * @return
     */
    byte[] getwxacode(String accessToken, String page, String scene, int width);

}
