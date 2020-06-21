package com.mcoding.modular.base.user.service.impl;

import com.mcoding.modular.base.user.entity.BaseUser;
import com.mcoding.modular.base.user.dao.BaseUserMapper;
import com.mcoding.modular.base.user.service.BaseUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
