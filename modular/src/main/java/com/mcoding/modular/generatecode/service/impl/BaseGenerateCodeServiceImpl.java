package com.mcoding.modular.generatecode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mcoding.base.core.doc.Step;
import com.mcoding.base.core.utils.SpringContextHolder;
import com.mcoding.base.common.exception.CommonException;
import com.mcoding.modular.generatecode.dao.BaseGenerateCodeDao;
import com.mcoding.modular.generatecode.entity.BaseGenerateCode;
import com.mcoding.modular.generatecode.service.BaseGenerateCodeService;
import com.mcoding.modular.generatecode.strategy.GenerateStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wzt on 2020/2/9.
 * @version 1.0
 */
@Service
public class BaseGenerateCodeServiceImpl extends ServiceImpl<BaseGenerateCodeDao, BaseGenerateCode> implements BaseGenerateCodeService {

    @Step(comment = "生成下个编码")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String generateNextCode(String targetCode) {
        // 通过乐观锁的方式顺序产生订单编码
        return this.generateNextCode(targetCode, 5);
    }

    private String generateNextCode(String targetCode, int retryTimes) {
        if (retryTimes == 0) {
            throw new CommonException("生成订单编码异常");
        }

        QueryWrapper<BaseGenerateCode> targetQueryWrapper = new QueryWrapper<>();
        targetQueryWrapper.lambda().eq(BaseGenerateCode::getTargetCode, targetCode);
        BaseGenerateCode baseGenerateCode = this.getOne(targetQueryWrapper);

        String strategy = baseGenerateCode.getStrategy();
        GenerateStrategy generateStrategy = null;
        try {
            generateStrategy = (GenerateStrategy) SpringContextHolder.getOneBean(Class.forName(strategy));
        } catch (ClassNotFoundException e) {
            throw new CommonException(e);
        }

        if (generateStrategy == null) {
            throw new CommonException(String.format("找不到类%s", strategy));
        }

        String nextCode = generateStrategy.generateNextCode(baseGenerateCode);

        BaseGenerateCode updateBaseGenerateCode = new BaseGenerateCode();
        updateBaseGenerateCode.setId(baseGenerateCode.getId());
        updateBaseGenerateCode.setCurrentCode(nextCode);
        // 乐观锁版本
        updateBaseGenerateCode.setVersion(baseGenerateCode.getVersion());
        boolean isSuccess = this.updateById(updateBaseGenerateCode);
        if (isSuccess) {
            return nextCode;
        } else {
            return this.generateNextCode(targetCode, retryTimes - 1);
        }
    }
}
