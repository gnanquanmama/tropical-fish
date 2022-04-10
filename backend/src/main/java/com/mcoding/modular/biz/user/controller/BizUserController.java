package com.mcoding.modular.biz.user.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.itextpdf.kernel.geom.PageSize;
import com.mcoding.base.common.util.Assert;
import com.mcoding.base.common.util.pdf.FtlToPdfUtil;
import com.mcoding.base.core.orm.DslParser;
import com.mcoding.base.core.rest.ResponseResult;
import com.mcoding.base.user.entity.BaseUser;
import com.mcoding.base.user.service.BaseUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 业务用户
 * </p>
 *
 * @author wzt
 * @since 2020-06-21
 */
@Slf4j
@Api(tags = "业务-用户服务")
@RestController
public class BizUserController {

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

    @ApiOperation(value = "查询用户详情")
    @GetMapping("/service/user/detail")
    public ResponseResult<BaseUser> detail(@RequestParam Integer id) {
        return ResponseResult.success(baseUserService.getById(id));
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/service/user/queryByPage")
    public ResponseResult<IPage<BaseUser>> queryByPage(@RequestBody JSONObject queryObject) {

        DslParser<BaseUser> dslParser = new DslParser<>(queryObject);
        QueryWrapper<BaseUser> queryWrapper = dslParser.parseToWrapper(BaseUser.class);

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
        httpServletResponse.setHeader("Content-Disposition", String.format("attachment;filename=%s.xlsx",
                new String("用户导入模板".getBytes("UTF-8"), "ISO8859-1")));
        httpServletResponse.addHeader("Cache-Control", "no-cache");

        InputStream templateIs = this.getFileFromClassPathResource("template/UserTemplate.xlsx");
        OutputStream outputStream = httpServletResponse.getOutputStream();

        ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(templateIs).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();

        FillConfig fillConfig = FillConfig.builder().build();
        excelWriter.fill(new FillWrapper("user", Collections.emptyList()), fillConfig, writeSheet);
        excelWriter.finish();
        outputStream.close();

        return null;
    }

    @ApiOperation("导入")
    @PostMapping(value = "/service/user/importByExcel")
    @ResponseBody
    public ResponseResult<String> importByExcel(@RequestParam("file") MultipartFile file) throws Exception {
        List<BaseUser> userList = EasyExcel.read(file.getInputStream(), BaseUser.class, new UserDataListener())
                .sheet()
                .doReadSync();
        log.info("userList = {}", JSON.toJSONString(userList));

        return ResponseResult.success();
    }

    @Resource
    private RedissonClient redissonClient;

    @ApiOperation("EXCEL导出，请求参数换取导出标识ID")
    @PostMapping(value = "/service/user/exchangeExportExcelId")
    @ResponseBody
    public ResponseResult<String> exchangeExportExcelId(@RequestBody JSONObject queryObject) {
        String exportExcelId = UUID.randomUUID().toString(true);
        redissonClient.getBucket("user::export::excel::" + exportExcelId)
                .set(queryObject, 30, TimeUnit.SECONDS);
        return ResponseResult.success(exportExcelId);
    }

    @ApiOperation("EXCEL导出")
    @GetMapping(value = "/service/user/exportByExcel/{exportExcelId}")
    @ResponseBody
    public ResponseResult<String> exportByExcel(
            @ApiParam("导出excel标识") @PathVariable("exportExcelId") String exportExcelId,
            HttpServletResponse httpServletResponse) throws Exception {

        JSONObject jsonObject = (JSONObject) this.redissonClient.getBucket("user::export::excel::" + exportExcelId).get();
        Assert.isNotNull(jsonObject, "导出excel标识ID已过期或者不不存在");

        String fileName = "用户明细" + DateUtil.format(new Date(), "yyyyMMddHHmmss");

        httpServletResponse.reset();
        httpServletResponse.setContentType("application/vnd.ms-excel;charset=utf-8");
        httpServletResponse.setHeader("Content-Disposition", String.format("attachment;filename=%s.xls",
                new String(fileName.getBytes("UTF-8"), "ISO8859-1")));
        httpServletResponse.addHeader("Cache-Control", "no-cache");

        InputStream templateIs = this.getFileFromClassPathResource("template/UserTemplate.xlsx");
        OutputStream outputStream = httpServletResponse.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(templateIs).build();
        WriteSheet writeSheet = EasyExcel.writerSheet().build();

        FillConfig fillConfig = FillConfig.builder().build();
        List<BaseUser> userList = this.getUserList(jsonObject);
        excelWriter.fill(new FillWrapper("user", userList), fillConfig, writeSheet);
        excelWriter.finish();
        outputStream.close();

        return null;
    }

    @ApiOperation("PDF导出")
    @GetMapping(value = "/service/user/exportByPdf/{exportExcelId}")
    @ResponseBody
    public void exportPdf(@ApiParam("导出excel标识") @PathVariable("exportExcelId") String exportPdfId,
            HttpServletResponse httpServletResponse) throws Exception {

        JSONObject jsonObject = (JSONObject) this.redissonClient.getBucket("user::export::excel::" + exportPdfId).get();
        Assert.isNotNull(jsonObject, "导出excel标识ID已过期或者不不存在");

        httpServletResponse.setContentType("application/x-msdownload");
        String fileName = URLEncoder.encode("用户明细.pdf", "UTF-8");
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        List<BaseUser> userList = this.getUserList(jsonObject);
        Map<String, Object> dataSource = Maps.newHashMap();
        dataSource.put("userList", userList);
        // 去读模板文件 -> 替换占位符 -> 生成 HTML 字节数组
        byte[] htmlByteArray = FtlToPdfUtil.generateHtmlByteArray("/template", "UserTemplate.ftl", dataSource);

        OutputStream outputStream = httpServletResponse.getOutputStream();
        // HTML 转换为 PDF
        FtlToPdfUtil.convertToPdf(htmlByteArray, outputStream, PageSize.A2);

        outputStream.flush();
        outputStream.close();
    }

    private List<BaseUser> getUserList(JSONObject jsonObject) {
        DslParser<BaseUser> dslParser = new DslParser<>(jsonObject);
        dslParser.parseToWrapper(BaseUser.class);
        QueryWrapper<BaseUser> queryWrapper = dslParser.getQueryWrapper();
        queryWrapper.lambda().orderByDesc(BaseUser::getCreateTime);
        return this.baseUserService.list(queryWrapper);
    }

    private InputStream getFileFromClassPathResource(String filePath) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        return classPathResource.getInputStream();
    }

}
