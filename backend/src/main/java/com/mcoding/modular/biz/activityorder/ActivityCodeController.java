package com.mcoding.modular.biz.activityorder;

import com.alibaba.fastjson.JSON;
import com.mcoding.base.core.rest.ResponseResult;
import com.mcoding.modular.biz.activityorder.domain.ActivityOrderBizCodeGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.javasimon.aop.Monitored;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Monitored
    @ApiOperation("生成订单活动编码")
    @PostMapping("/service/activityorder/generateBizCode")
    public ResponseResult<Integer> generateBizCode(@RequestParam int quantity) {

        List<String> codeList = IntStream.rangeClosed(1, quantity)
                .parallel()
                .mapToObj(num -> tradeOrderBizCodeGenerator.generateNextCode())
                .sorted()
                .collect(Collectors.toList());

        log.info("biz code List = {}", JSON.toJSONString(codeList));

        return ResponseResult.success(codeList.size());
    }

    @Monitored
    @ApiOperation("批量生成订单活动编码")
    @PostMapping("/service/activityorder/generateBizCodeList")
    public ResponseResult<Integer> generateBizCodeList(@RequestParam int quantity) {

        List<String> codeList = this.tradeOrderBizCodeGenerator.generateBizCodeList(quantity);

        log.info("biz code List = {}", JSON.toJSONString(codeList));

        return ResponseResult.success(codeList.size());
    }


}
