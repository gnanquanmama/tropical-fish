package com.mcoding.modular.generatecode.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mcoding.base.doc.Process;
import com.mcoding.base.orm.SmartWrapper;
import com.mcoding.base.rest.ResponseResult;
import com.mcoding.modular.auth.LoginRequired;
import com.mcoding.modular.base.user.entity.BaseUser;
import com.mcoding.modular.generatecode.entity.BaseGenerateCode;
import com.mcoding.modular.generatecode.service.BaseGenerateCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.javasimon.aop.Monitored;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @author wzt on 2020/2/9.
 * @version 1.0
 */
@Slf4j
@Api(tags = "基础-生成编码服务")
@RestController
public class BaseGenerateCodeController {

    @Resource
    private BaseGenerateCodeService baseGenerateCodeService;

    @Process(comment = "生成编码")
    @Monitored
    @ApiOperation("生成编码")
    @PostMapping("/service/generateCode/generateNextCode")
    public ResponseResult<String> generateNextCode(@ApiIgnore @LoginRequired BaseUser loginUser,
                                                   @ApiParam("目标记录编码") @RequestParam String targetCode) {

        log.info("current user is {}", JSON.toJSONString(loginUser));

        return ResponseResult.success(this.baseGenerateCodeService.generateNextCode(targetCode));
    }

    @Monitored
    @ApiOperation("分页查询")
    @PostMapping("/service/bigPackageOrder/queryByPage")
    public ResponseResult<IPage<BaseGenerateCode>> queryByPage(@ApiIgnore @LoginRequired BaseUser loginUser,
                                                               @RequestBody JSONObject queryObject) {

        log.info("current user is {}", JSON.toJSONString(loginUser));

        SmartWrapper<BaseGenerateCode> smartWrapper = new SmartWrapper<>();
        smartWrapper.parse(queryObject, BaseGenerateCode.class);

        QueryWrapper<BaseGenerateCode> queryWrapper = smartWrapper.getQueryWrapper();
        queryWrapper.lambda()
                .orderByDesc(BaseGenerateCode::getCreateTime);

        IPage<BaseGenerateCode> page = smartWrapper.generatePage();
        this.baseGenerateCodeService.page(page, queryWrapper);

        return ResponseResult.success(page);
    }


}
