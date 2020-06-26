package com.mcoding.modular.activityorder;

import com.mcoding.base.core.doc.Process;
import com.mcoding.base.core.rest.ResponseResult;
import com.mcoding.modular.activityorder.domain.ActivityOrderBizCodeGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.javasimon.aop.Monitored;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wzt on 2020/2/9.
 * @version 1.0
 */
@Slf4j
@Api(tags = "业务-活动订单服务")
@RestController
public class ActivityCodeController {

    @Resource
    private ActivityOrderBizCodeGenerator tradeOrderBizCodeGenerator;

    @Process(comment = "生成订单活动编码")
    @Monitored
    @ApiOperation("生成订单活动编码")
    @PostMapping("/service/activityorder/generateBizCode")
    public ResponseResult<String> generateBizCode() {

        return ResponseResult.success(this.tradeOrderBizCodeGenerator.generateNextCode());
    }


}
