package com.mcoding.modular.generatecode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mcoding.base.common.exception.CommonException;
import com.mcoding.base.core.doc.Step;
import com.mcoding.base.core.utils.SpringContextHolder;
import com.mcoding.modular.generatecode.dao.BaseGenerateCodeDao;
import com.mcoding.modular.generatecode.entity.BaseGenerateCode;
import com.mcoding.modular.generatecode.service.BaseGenerateCodeService;
import com.mcoding.modular.generatecode.strategy.GenerateStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wzt on 2020/2/9.
 * @version 1.0
 */
@Service
public class BaseGenerateCodeServiceImpl extends ServiceImpl<BaseGenerateCodeDao, BaseGenerateCode> implements BaseGenerateCodeService {

    @Step(comment = "生成下个编码")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<String> generateBizCodeList(String targetCode, int quantity) {

        QueryWrapper<BaseGenerateCode> targetQueryWrapper = new QueryWrapper<>();
        targetQueryWrapper.lambda().eq(BaseGenerateCode::getTargetCode, targetCode);
        BaseGenerateCode baseGenerateCode = this.getOne(targetQueryWrapper);

        // 批量占用 quantity 个数之后， 返回最后一个业务编码
        String lastBizCode = this.generateLastCode(baseGenerateCode.getId(), quantity, (byte) 5);

        String maxCode = baseGenerateCode.getMaxCode();
        int maxCodeLength = maxCode.length();
        int lastBizCodeLength = lastBizCode.length();

        int constStrLength = lastBizCodeLength - maxCodeLength;

        String constStr = StringUtils.substring(lastBizCode, 0, constStrLength);
        String incrementStr = StringUtils.substring(lastBizCode, constStrLength + 1);

        BigDecimal incrementNum = new BigDecimal(incrementStr);

        List<String> codeList = Lists.newArrayList();

        for (int i = 0; i < quantity; i++) {
            BigDecimal previousNum = incrementNum.subtract(BigDecimal.valueOf(i));
            codeList.add(constStr + StringUtils.leftPad(previousNum.toString(), maxCodeLength, "0"));
        }

        return Lists.reverse(codeList);
    }

    /**
     * 通过乐观锁的方式顺序产生订单编码
     *
     * @param generateCodeId
     * @param quantity  数量
     * @param retryTimes 重试次数
     * @return
     */
    private String generateLastCode(String generateCodeId, int quantity, byte retryTimes) {
        if (retryTimes == 0) {
            throw new CommonException("生成订单编码异常");
        }

        BaseGenerateCode baseGenerateCode = this.getById(generateCodeId);

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

        String lastCode = generateStrategy.generateListCode(baseGenerateCode, quantity);

        BaseGenerateCode updateBaseGenerateCode = new BaseGenerateCode();
        updateBaseGenerateCode.setId(baseGenerateCode.getId());
        updateBaseGenerateCode.setCurrentCode(lastCode);
        // 乐观锁版本
        updateBaseGenerateCode.setVersion(baseGenerateCode.getVersion());
        boolean isSuccess = this.updateById(updateBaseGenerateCode);
        if (isSuccess) {
            return lastCode;
        }

        return this.generateLastCode(generateCodeId, quantity, (byte) (retryTimes - 1));
    }
}
