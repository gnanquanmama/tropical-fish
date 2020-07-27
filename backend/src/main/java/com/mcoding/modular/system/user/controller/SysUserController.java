package com.mcoding.modular.system.user.controller;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.mcoding.base.core.orm.DslParser;
import com.mcoding.base.core.rest.*;

import com.mcoding.modular.system.user.service.SysUserService;
import com.mcoding.modular.system.user.entity.SysUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author wzt
 * @since 2020-07-27
 */
@Api(tags = "管理员服务")
@RestController
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @ApiOperation("创建")
    @PostMapping("/service/sysuser/create")
    public ResponseResult<String> create(@Valid @RequestBody SysUser sysUser) {
        sysUserService.save(sysUser);
        return ResponseResult.success();
    }

    @ApiOperation(value = "删除")
    @PostMapping("/service/sysuser/delete")
    public ResponseResult<String> delete(@RequestParam Integer id) {
        sysUserService.removeById(id);
        return ResponseResult.success();
    }

    @ApiOperation(value = "修改")
    @PostMapping("/service/sysuser/modify")
    public ResponseResult<String> modify(@Valid @RequestBody SysUser sysUser) {
        sysUserService.updateById(sysUser);
        return ResponseResult.success();
    }

    @ApiOperation(value = "查询活动详情")
    @GetMapping("/service/sysuser/detail")
    public ResponseResult<SysUser> detail(@RequestParam Integer id) {
        return ResponseResult.success(sysUserService.getById(id));
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/service/sysuser/queryByPage")
    public ResponseResult<IPage<SysUser>> queryByPage(@RequestBody JSONObject queryObject) {

         DslParser<SysUser> dslParser = new DslParser<>();
         QueryWrapper<SysUser> queryWrapper = dslParser.parseToWrapper(queryObject, SysUser.class);

         IPage<SysUser> page = dslParser.generatePage();
         sysUserService.page(page, queryWrapper);
         return ResponseResult.success(page);
    }

}
