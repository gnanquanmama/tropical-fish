package com.fastdev.modular.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastdev.entity.ResponseResult;
import com.fastdev.modular.demo.controller.dto.DemoDTO;
import com.fastdev.modular.demo.entity.BaseOperationLog;
import com.fastdev.modular.demo.service.IBaseOperationLogService;
import com.fastdev.util.BeanMapperUtils;
import com.fastdev.util.QueryPageUtils;
import com.fastdev.util.WrapperUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

@Api(description = "开发规范DEMO")
@RestController
public class DemoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IBaseOperationLogService demoService;

    @ApiOperation(value = "创建")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseResult<String> create(@Valid @RequestBody DemoDTO demoDTO) {

        logger.info(String.format("EVENT=保存接口|CONTENT=%s", JSON.toJSONString(demoDTO)));

        //DTO转换为领域对象
        BaseOperationLog baseOperationLog = BeanMapperUtils.map(demoDTO, BaseOperationLog.class);
        baseOperationLog.setOperationTime(new Date());
        this.demoService.save(baseOperationLog);

        return ResponseResult.success();
    }

    @ApiOperation("读取")
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public ResponseResult<BaseOperationLog> read(@RequestBody String id) {
        BaseOperationLog record = this.demoService.getById(id);
        return ResponseResult.success(record);
    }

    @ApiOperation("更改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult<String> update(@RequestBody DemoDTO demoDTO) {
        BaseOperationLog baseOperationLog = BeanMapperUtils.map(demoDTO, BaseOperationLog.class);
        this.demoService.updateById(baseOperationLog);
        return ResponseResult.success();
    }

    @ApiOperation("删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult<String> delete(@RequestBody String id) {
        this.demoService.removeById(id);
        return ResponseResult.success();
    }

    @ApiOperation("分页查询")
    @RequestMapping(value = "/queryByPage", method = RequestMethod.POST)
    public ResponseResult<Page<DemoDTO>> queryByPage(@RequestBody(required = false) JSONObject queryObject) {

        QueryWrapper<BaseOperationLog> queryWrapper = WrapperUtils.createWrapper(queryObject);
        Page<BaseOperationLog> page = QueryPageUtils.createPage(queryObject);

        IPage<BaseOperationLog> queryResult = demoService.page(page, queryWrapper);

        Page<DemoDTO> responseResult = new Page<>();
        responseResult.setCurrent(queryResult.getCurrent());
        responseResult.setSize(queryResult.getSize());
        responseResult.setTotal(queryResult.getTotal());
        responseResult.setRecords(BeanMapperUtils.mapAsList(queryResult.getRecords(), DemoDTO.class));

        return ResponseResult.success(responseResult);
    }

}
