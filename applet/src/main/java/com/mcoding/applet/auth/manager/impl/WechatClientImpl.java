package com.mcoding.applet.auth.manager.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mcoding.applet.auth.business.UserInfoBo;
import com.mcoding.applet.auth.business.resp.AccessTokenRespEntity;
import com.mcoding.applet.auth.business.resp.JsCode2SessionRespEntity;
import com.mcoding.applet.auth.manager.WechatClient;
import com.mcoding.base.common.exception.CommonException;
import com.mcoding.base.core.cache.RCacheEvict;
import com.mcoding.base.core.cache.RCacheable;
import com.mcoding.base.core.doc.Phase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wzt on 2019/11/12.
 * @version 1.0
 */
@Slf4j
@Service
public class WechatClientImpl implements WechatClient {

    @Resource
    private RestTemplate restTemplate;

    @Value("${wechat.appid}")
    private String appID;

    @Value("${wechat.secret}")
    private String appSecret;

    @Phase(comment = "根据jscode获取用户信息")
    @Override
    public UserInfoBo getUserInfoByCode(String code) {
        String requestUrl = this.buildJscode2sessionUrl(code);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(requestUrl, String.class);
        log.info("EVENT=根据code获取用户信息|request_url={}|response_result={}", requestUrl, responseEntity);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JsCode2SessionRespEntity body = JSON.parseObject(responseEntity.getBody(), JsCode2SessionRespEntity.class);
            if (StringUtils.isNotBlank(body.getSession_key())) {
                UserInfoBo userInfoBo = new UserInfoBo();
                userInfoBo.setSessionKey(body.getSession_key());
                userInfoBo.setOpenId(body.getOpenid());
                userInfoBo.setUnionid(body.getUnionid());
                return userInfoBo;
            }
            throw new CommonException(body.getErrmsg());
        }

        throw new CommonException("调用微信接口异常");
    }

    @Phase(comment = "获取小程序access_token")
    @RCacheable(key = "dmt::wechat::global::AccessToken", ttl = 7000, timeUnit = TimeUnit.SECONDS,
            resetTTL = false, serial = true)
    @Override
    public String getAccessToken() {
        String requestUrl = this.buildAccessTokenUrl();
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(requestUrl, String.class);
        log.info("EVENT=获取微信小程序access_token|request_url={}|response_result={}", requestUrl, responseEntity);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            AccessTokenRespEntity body = JSON.parseObject(responseEntity.getBody(), AccessTokenRespEntity.class);
            return body.getAccess_token();
        }

        throw new CommonException("调用微信接口异常");
    }

    @RCacheEvict(key = "dmt::wechat::global::AccessToken")
    @Override
    public String evictAccessToken() {
        return null;
    }

    @Phase(comment = "调用微信服务，生成二维码字节流")
    @Override
    public byte[] getwxacode(String accessToken, String page, String scene, int width) {
        String requestUrl = this.buildGetwxacodeUrl(accessToken);

        Map<String, Object> params = Maps.newHashMap();
        params.put("scene", scene);
        params.put("width", width);
        if (StringUtils.isNotBlank(page)) {
            params.put("page", page);
        }

        log.info("EVENT=调用微信服务，生成二维码字节流|REQUEST_PARAM={}", JSON.toJSONString(params));

        byte[] byteArray = null;
        ResponseEntity<byte[]> entity = restTemplate.postForEntity(requestUrl, JSON.toJSONString(params), byte[].class);

        // 图片或错误信息
        byteArray = entity.getBody();
        String wxReturnStr = new String(byteArray);

        if (wxReturnStr.indexOf("errcode") != -1) {
            JSONObject json = JSONObject.parseObject(wxReturnStr);
            String errcode = json.get("errcode").toString();
            String errmsg = json.get("errmsg").toString();
            throw new CommonException(errcode + errmsg);
        }

        return byteArray;
    }

    private String buildGetwxacodeUrl(String accessToken) {
        return String.format("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s", accessToken);
    }

    private String buildAccessTokenUrl() {
        return String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                appID, appSecret);
    }

    private String buildJscode2sessionUrl(String jsCode) {
        return String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%1$s&secret=%2$s&js_code=%3$s&grant_type=authorization_code",
                appID, appSecret, jsCode);
    }

}

