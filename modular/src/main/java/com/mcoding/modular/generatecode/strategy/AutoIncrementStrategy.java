package com.mcoding.modular.generatecode.strategy;

import com.mcoding.base.common.exception.CommonException;
import com.mcoding.modular.generatecode.entity.BaseGenerateCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


/**
 * 编码自增策略
 * 从 startCode开始，每个code加1，当code值等于maxCode的时候，code设回startCode，重新开始。如果maxCode为空，就会一直加下去。
 *
 * @author hzy
 */
@Component
public class AutoIncrementStrategy extends GenerateStrategy {

    @Override
    public String generateNextCode(BaseGenerateCode currentCode) {
        String code = currentCode.getCurrentCode();
        if (StringUtils.isBlank(code)) {
            code = currentCode.getStartCode();
        }

        if (StringUtils.isBlank(code)) {
            throw new CommonException("配置异常，编码生成规则中，没有起始编码");
        }

        if (code.equals(currentCode.getMaxCode())) {
            throw new CommonException("流水号已经到了最大值，无法生成流水号了");
        }

        BigDecimal bigDecimal = new BigDecimal(code);
        return bigDecimal.add(new BigDecimal(1)).toString();
    }

}
