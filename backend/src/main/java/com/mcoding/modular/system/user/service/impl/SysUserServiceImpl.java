package com.mcoding.modular.system.user.service.impl;

import com.mcoding.modular.system.user.entity.SysUser;
import com.mcoding.modular.system.user.dao.SysUserMapper;
import com.mcoding.modular.system.user.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author wzt
 * @since 2020-07-27
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
