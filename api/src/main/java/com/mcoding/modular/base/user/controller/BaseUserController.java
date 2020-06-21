package com.mcoding.modular.base.user.controller;


import com.mcoding.base.orm.SmartWrapper;
import com.mcoding.base.rest.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.mcoding.modular.base.user.service.BaseUserService;
import com.mcoding.modular.base.user.entity.BaseUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 基础用户
 * </p>
 *
 * @author wzt
 * @since 2020-06-21
 */
@Api(tags = "基础用户服务")
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

         SmartWrapper<BaseUser> smartWrapper = new SmartWrapper<>();
         smartWrapper.parse(queryObject, BaseUser.class);

         QueryWrapper<BaseUser> queryWrapper = smartWrapper.getQueryWrapper();
         IPage<BaseUser> page = smartWrapper.generatePage();
         baseUserService.page(page, queryWrapper);
         return ResponseResult.success(page);
    }

}
