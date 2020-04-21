package com.mcoding.modular.generatecode.controller;

import com.mcoding.common.util.rest.ResponseResult;
import com.mcoding.modular.generatecode.service.BaseGenerateCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wzt on 2020/2/9.
 * @version 1.0
 */
@Api(tags = "基础-生成编码服务")
@RestController
public class BaseGenerateCodeController {

    @Resource
    private BaseGenerateCodeService baseGenerateCodeService;

    @ApiOperation("生成编码")
    @PostMapping("/service/generateCode/generateNextCode")
    public ResponseResult<String> generateNextCode(String targetCode) {
        return ResponseResult.success(this.baseGenerateCodeService.generateNextCode(targetCode));
    }


}
