package com.mcoding.applet.auth.bo;

import lombok.Data;

/**
 * @author wzt on 2020/3/25.
 * @version 1.0
 */
@Data
public class JsCode2SessionRespEntity {

    private String session_key;
    private String openid;
    private String unionid;
    private String errcode;
    private String errmsg;

}
