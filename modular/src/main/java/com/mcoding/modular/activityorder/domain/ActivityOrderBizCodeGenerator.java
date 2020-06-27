package com.mcoding.modular.activityorder.domain;

import com.google.common.collect.Range;
import com.mcoding.base.common.exception.BizException;
import com.mcoding.modular.generatecode.contant.TargetCodeEnum;
import com.mcoding.modular.generatecode.domain.CommonBizCodeGenerator;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wzt on 2020/6/26.
 * @version 1.0
 */
@Component
public class ActivityOrderBizCodeGenerator extends CommonBizCodeGenerator {

    public ActivityOrderBizCodeGenerator() {
        this.setTargetCode(TargetCodeEnum.BIG_PACKAGE_ACTIVITY_ORDER.getTargetCode());
        this.setCacheQuantity(100);
    }

    @Override
    public String generateNextCode() {
        return super.generateNextCode();
    }

    @Override
    public List<String> generateBizCodeList(int quantity) {
        if (!Range.closed(1, 5000).contains(quantity)) {
            throw new BizException("批量生成的数量必须在1 到 5000 之间");
        }

        return super.generateBizCodeList(quantity);
    }
}
