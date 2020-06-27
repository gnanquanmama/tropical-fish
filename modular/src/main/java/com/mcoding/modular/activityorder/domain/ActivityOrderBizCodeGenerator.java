package com.mcoding.modular.activityorder.domain;

import com.mcoding.modular.generatecode.contant.TargetCodeEnum;
import com.mcoding.modular.generatecode.domain.AbstractBizCodeGenerator;
import org.springframework.stereotype.Component;

/**
 * @author wzt on 2020/6/26.
 * @version 1.0
 */
@Component
public class ActivityOrderBizCodeGenerator extends AbstractBizCodeGenerator {

    public ActivityOrderBizCodeGenerator() {
        this.setTargetCode(TargetCodeEnum.BIG_PACKAGE_ACTIVITY_ORDER.getTargetCode());
        this.setCacheQuantity(100);
    }

    @Override
    public String generateNextCode() {
        return super.generateNextCode();
    }

}
