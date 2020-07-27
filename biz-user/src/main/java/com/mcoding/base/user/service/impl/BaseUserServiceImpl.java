package com.mcoding.base.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mcoding.base.core.doc.Phase;
import com.mcoding.base.user.entity.BaseUser;
import com.mcoding.base.user.dao.BaseUserMapper;
import com.mcoding.base.user.service.BaseUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 基础用户 服务实现类
 * </p>
 *
 * @author wzt
 * @since 2020-06-21
 */
@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements BaseUserService {


    @Phase(comment = "根据openId获取用户信息")
    @Override
    public BaseUser getUserByOpenId(String openId) {
        QueryWrapper<BaseUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BaseUser::getOpenId, openId);
        return this.getOne(queryWrapper);
    }

}
