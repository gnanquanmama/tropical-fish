package strategy;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.mcoding.base.common.exception.CommonException;
import com.mcoding.modular.biz.generatecode.entity.BaseGenerateCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class DateIncrementStrategy extends GenerateStrategy {

    @Override
    public String generateListCode(BaseGenerateCode generateCode, int quantity) {
        String currentCode = generateCode.getCurrentCode();
        String maxCode = generateCode.getMaxCode();
        if (StringUtils.isBlank(maxCode)) {
            maxCode = "99999";
        }

        int maxLength = maxCode.length();
        String currentDateStr = DateUtil.format(new Date(), "yyyyMMdd");
        if (StringUtils.isBlank(currentCode)) {
            return currentDateStr + StringUtils.leftPad(String.valueOf(quantity), maxLength, "0");
        }

        // 日期前缀不存在
        if (StrUtil.indexOf(currentCode, currentDateStr, 0, false) != 0) {
            return currentDateStr + StringUtils.leftPad(String.valueOf(quantity), maxLength, "0");
        }

        String currentIncrementStr = currentCode.replaceFirst(currentDateStr, "");
        BigDecimal incrementStr = new BigDecimal(currentIncrementStr).add(BigDecimal.valueOf(quantity));

        BigDecimal maxCodeBd = new BigDecimal(maxCode);
        if (maxCodeBd.compareTo(incrementStr) < 0) {
            throw new CommonException("流水号已经到了最大值，无法生成流水号了");
        }

        return currentDateStr + StringUtils.leftPad(incrementStr.toString(), maxLength, "0");


    }

}
