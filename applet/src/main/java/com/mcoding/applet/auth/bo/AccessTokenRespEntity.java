package com.mcoding.applet.auth.bo;

import lombok.Data;

/**
 * @author wzt on 2020/3/25.
 * @version 1.0
 */
@Data
public class AccessTokenRespEntity {

    private String access_token;
    private Long expires_in;
    private String errcode;
    private String errmsg;

}
