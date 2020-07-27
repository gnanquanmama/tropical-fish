package com.mcoding.applet.auth.bo;

import lombok.Data;

/**
 * @author wzt on 2019/11/12.
 * @version 1.0
 */
@Data
public class UserInfoBo {

    private String sessionKey;

    private String openId;

    private String unionid;
}
