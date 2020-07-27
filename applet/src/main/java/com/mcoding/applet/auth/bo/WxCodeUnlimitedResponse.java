package com.mcoding.applet.auth.bo;

import lombok.Data;

/**
 * @author wzt on 2020/3/26.
 * @version 1.0
 */
@Data
public class WxCodeUnlimitedResponse {

    /**
     * 请求失败错误码
     */
    private String errcode;

    /**
     * 请求失败错误信息
     */
    private String errmsg;

    /**
     * 图片信息
     */
    private byte[] buffer;

}