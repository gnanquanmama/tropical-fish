package com.mcoding.modular.biz.generatecode.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mcoding.base.core.doc.Process;
import com.mcoding.base.core.orm.DslParser;
import com.mcoding.base.core.rest.ResponseResult;
import com.mcoding.modular.base.auth.LoginRequired;
import com.mcoding.base.user.entity.BaseUser;
import com.mcoding.modular.biz.activityorder.domain.ActivityOrderBizCodeGenerator;
import com.mcoding.modular.biz.generatecode.entity.BaseGenerateCode;
import com.mcoding.modular.biz.generatecode.service.BaseGenerateCodeService;
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
import java.util.List;

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

    @Resource
    private ActivityOrderBizCodeGenerator tradeOrderBizCodeGenerator;

    @Process(comment = "生成编码")
    @Monitored
    @ApiOperation("生成编码")
    @PostMapping("/service/generateCode/generateListCode")
    public ResponseResult<List<String>> generateListCode(@ApiIgnore @LoginRequired BaseUser loginUser,
                                                         @ApiParam("目标记录编码") @RequestParam String targetCode,
                                                         @ApiParam("数量") @RequestParam int quantity) {

        log.info("current user is {}", JSON.toJSONString(loginUser));

        return ResponseResult.success(this.baseGenerateCodeService.generateBizCodeList(targetCode, quantity));
    }

    @Monitored
    @ApiOperation("分页查询")
    @PostMapping("/service/bigPackageOrder/queryByPage")
    public ResponseResult<IPage<BaseGenerateCode>> queryByPage(@ApiIgnore @LoginRequired BaseUser loginUser,
                                                               @RequestBody JSONObject queryObject) {

        log.info("current user is {}", JSON.toJSONString(loginUser));

        DslParser<BaseGenerateCode> dslParser = new DslParser<>();
        QueryWrapper<BaseGenerateCode> queryWrapper = dslParser.parseToWrapper(queryObject, BaseGenerateCode.class);

        queryWrapper.lambda()
                .orderByDesc(BaseGenerateCode::getCreateTime);

        IPage<BaseGenerateCode> page = dslParser.generatePage();
        this.baseGenerateCodeService.page(page, queryWrapper);

        return ResponseResult.success(page);
    }


}
