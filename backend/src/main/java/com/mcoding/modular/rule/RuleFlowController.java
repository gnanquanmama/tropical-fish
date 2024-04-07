package com.mcoding.modular.rule;

import com.mcoding.base.core.rest.ResponseResult;
import com.mcoding.modular.system.user.entity.SysUser;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.javasimon.aop.Monitored;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wzt on 2020/6/19.
 * @version 1.0
 */
@Api(tags = "基础-规则引擎")
@RestController
public class RuleFlowController {

    @Resource
    private BizFlow bizFlow;

    @Monitored
    @ApiOperation(value = "执行")
    @PostMapping("/noLogin/ruleFlow")
    public ResponseResult execute() {

        bizFlow.execute();
        return ResponseResult.success();
    }

}