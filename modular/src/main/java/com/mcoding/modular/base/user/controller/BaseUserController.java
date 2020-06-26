package com.mcoding.modular.base.user.controller;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mcoding.base.core.orm.DslParser;
import com.mcoding.base.core.rest.ResponseResult;
import com.mcoding.base.common.util.excel.ExcelUtils;
import com.mcoding.modular.base.user.entity.BaseUser;
import com.mcoding.modular.base.user.service.BaseUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jxl.write.WritableWorkbook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础用户
 * </p>
 *
 * @author wzt
 * @since 2020-06-21
 */
@Slf4j
@Api(tags = "基础-用户服务")
@RestController
public class BaseUserController {

    @Resource
    private BaseUserService baseUserService;

    @ApiOperation("创建")
    @PostMapping("/service/user/create")
    public ResponseResult<String> create(@Valid @RequestBody BaseUser baseUser) {
        baseUserService.save(baseUser);
        return ResponseResult.success();
    }

    @ApiOperation(value = "删除")
    @PostMapping("/service/user/delete")
    public ResponseResult<String> delete(@RequestParam Integer id) {
        baseUserService.removeById(id);
        return ResponseResult.success();
    }

    @ApiOperation(value = "修改")
    @PostMapping("/service/user/modify")
    public ResponseResult<String> modify(@Valid @RequestBody BaseUser baseUser) {
        baseUserService.updateById(baseUser);
        return ResponseResult.success();
    }

    @ApiOperation(value = "查询活动详情")
    @GetMapping("/service/user/detail")
    public ResponseResult<BaseUser> detail(@RequestParam Integer id) {
        return ResponseResult.success(baseUserService.getById(id));
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/service/user/queryByPage")
    public ResponseResult<IPage<BaseUser>> queryByPage(@RequestBody JSONObject queryObject) {

        DslParser<BaseUser> dslParser = new DslParser<>();
        QueryWrapper<BaseUser> queryWrapper = dslParser.parseToWrapper(queryObject, BaseUser.class);

        IPage<BaseUser> page = dslParser.generatePage();
        baseUserService.page(page, queryWrapper);
        return ResponseResult.success(page);
    }

    @ApiOperation("导出模板")
    @GetMapping(value = "/service/user/exportExcelTemplate")
    @ResponseBody
    public ResponseResult<String> exportExcelTemplate(HttpServletResponse httpServletResponse) throws Exception {

        httpServletResponse.reset();
        httpServletResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
        httpServletResponse.setHeader("Content-Disposition", String.format("attachment;filename=%s.xls",
                new String("用户导入模板".getBytes("UTF-8"), "ISO8859-1")));
        httpServletResponse.addHeader("Cache-Control", "no-cache");

        OutputStream outputStream = httpServletResponse.getOutputStream();

        WritableWorkbook writableWorkbook = ExcelUtils.exportDataToExcel(
                outputStream, BaseUser.class, Collections.emptyList(), "用户", null, 0);

        writableWorkbook.write();
        writableWorkbook.close();

        return null;
    }


    @ApiOperation("导出")
    @GetMapping(value = "/service/user/exportByExcel")
    @ResponseBody
    public ResponseResult<String> exportByExcel(
            @RequestParam(required = false) Map<String, Object> queryParam,
            HttpServletResponse httpServletResponse) throws Exception {

        String fileName = "用户明细" + DateUtil.format(new Date(), "yyyyMMddHHmmss");

        httpServletResponse.reset();
        httpServletResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
        httpServletResponse.setHeader("Content-Disposition", String.format("attachment;filename=%s.xls",
                new String(fileName.getBytes("UTF-8"), "ISO8859-1")));
        httpServletResponse.addHeader("Cache-Control", "no-cache");

        DslParser<BaseUser> dslParser = new DslParser<>();
        dslParser.parseToWrapper(new JSONObject(queryParam), BaseUser.class);

        QueryWrapper<BaseUser> queryWrapper = dslParser.getQueryWrapper();
        queryWrapper.lambda().orderByDesc(BaseUser::getCreateTime);
        List<BaseUser> activityOrderList = this.baseUserService.list(queryWrapper);

        OutputStream outputStream = httpServletResponse.getOutputStream();

        WritableWorkbook writableWorkbook = ExcelUtils.exportDataToExcel(
                outputStream, BaseUser.class, activityOrderList, "用户", null, 0);

        writableWorkbook.write();
        writableWorkbook.close();

        return null;
    }

    @ApiOperation("导入")
    @PostMapping(value = "/service/user/importByExcel")
    @ResponseBody
    public ResponseResult<String> importByExcel(@RequestParam("file") MultipartFile file) throws Exception {

        List<BaseUser> userList = ExcelUtils.importExcelDataToList(
                file.getInputStream(), 0, 1, 0, BaseUser.class);

        log.info("file name is {} , import data is {}", file.getOriginalFilename(), JSON.toJSONString(userList));

        return ResponseResult.success();
    }


}
