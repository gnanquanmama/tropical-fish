package com.mcoding.applet.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcoding.applet.auth.dao.BaseUserTokenMapper;
import com.mcoding.applet.auth.entity.BaseUserToken;
import com.mcoding.applet.auth.service.BaseUserTokenService;
import com.mcoding.base.core.doc.Phase;
import jodd.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * <p>
 * 用户授权token 服务实现类
 * </p>
 *
 * @author wzt
 * @since 2020-04-20
 */
@Slf4j
@Service
public class BaseUserTokenServiceImpl extends ServiceImpl<BaseUserTokenMapper, BaseUserToken> implements BaseUserTokenService {

    @Phase(comment = "保存新token")
    @Override
    public void saveNewToken(int userId, String newToken) {

        BaseUserToken tokenEntity = new BaseUserToken();
        tokenEntity.setUserId(userId);

        String fake = RandomUtil.randomString(5);
        String encryptToken = Base64.encodeToString(newToken);
        tokenEntity.setAuthToken(fake + encryptToken);

        BaseUserToken baseUserToken = this.getById(userId);
        if (baseUserToken == null) {
            tokenEntity.setCreateTime(new Date());
            this.save(tokenEntity);
        } else {
            this.updateById(tokenEntity);
        }

    }

    @Override
    public String getToken(int userId) {
        BaseUserToken baseUserToken = this.getById(userId);
        String encryptToken = Optional.ofNullable(baseUserToken).map(BaseUserToken::getAuthToken).orElse("");
        if (StringUtils.isEmpty(encryptToken)) {
            return null;
        }

        String base64Token = StrUtil.sub(encryptToken, 5, encryptToken.length());
        return Base64.decodeToString(base64Token);
    }

}
