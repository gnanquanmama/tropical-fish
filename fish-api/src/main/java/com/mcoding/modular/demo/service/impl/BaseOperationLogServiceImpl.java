package com.mcoding.modular.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcoding.modular.demo.dao.BaseOperationLogMapper;
import com.mcoding.modular.demo.entity.BaseOperationLog;
import com.mcoding.modular.demo.service.IBaseOperationLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author wzt
 * @since 2018-11-30
 */
@Service
public class BaseOperationLogServiceImpl extends ServiceImpl<BaseOperationLogMapper, BaseOperationLog> implements IBaseOperationLogService {

}
