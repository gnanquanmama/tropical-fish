package com.mcoding.modular.generatecode.controller;

import com.mcoding.common.util.ResponseResult;
import com.mcoding.modular.generatecode.service.BaseGenerateCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wzt on 2020/2/9.
 * @version 1.0
 */
@Api(tags = "生成编码服务")
@RestController
public class BaseGenerateCodeController {

    @Resource
    private BaseGenerateCodeService baseGenerateCodeService;

    @Resource
    private RedissonClient redissonClient;

    @ApiOperation("生成编码")
    @PostMapping("/service/generateCode/generateNextCode")
    public ResponseResult<String> generateNextCode(String targetCode) {
        return ResponseResult.success(this.baseGenerateCodeService.generateNextCode(targetCode));
    }

    @ApiOperation("获取当前编码")
    @PostMapping("/service/generateCode/getCurrentCode")
    public ResponseResult<String> getCurrentCode(String targetCode) {
        return ResponseResult.success(this.baseGenerateCodeService.currentCode(targetCode));
    }

}
