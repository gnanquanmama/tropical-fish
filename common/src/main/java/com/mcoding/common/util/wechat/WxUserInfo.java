package com.mcoding.common.util.wechat;

import lombok.Data;

/**
 * @author wzt on 2020/4/15.
 * @version 1.0
 */
@Data
public class WxUserInfo {

    private String openId;//微信对应小程序的唯一标识
    private String nickName;//用户昵称
    private int gender;//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String language;//用户的语言，简体中文为zh_CN
    private String city;//用户所在城市
    private String province;//用户所在省份
    private String country;//用户所在国家
    private String avatarUrl;//用户头像
    private String unionId;//微信对应开放平台唯一标识
    private String phoneNumber;//手机号
    private String purePhoneNumber;//手机号
    private String countryCode;//国家编码
    private WxWaterMark watermark;//水印信息



}
