package ${package.Controller};


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.mcoding.util.*;

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * ${table.comment!}
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Api(tags = "${table.comment!}服务")
@RestController
public class ${table.controllerName} {

    @Resource
    private ${table.serviceName} ${table.serviceName ? uncap_first};

    @ApiOperation("创建")
    @PostMapping("/service/${package.ModuleName}/create")
    public ResponseResult<String> create(@Valid @RequestBody ${entity} ${entity ? uncap_first}) {
        ${table.serviceName ? uncap_first}.save(${entity ? uncap_first});
        return ResponseResult.success();
    }

    @ApiOperation(value = "删除")
    @PostMapping("/service/${package.ModuleName}/delete")
    public ResponseResult<String> delete(@RequestParam Integer id) {
        ${table.serviceName ? uncap_first}.removeById(id);
        return ResponseResult.success();
    }

    @ApiOperation(value = "修改")
    @PostMapping("/service/${package.ModuleName}/modify")
    public ResponseResult<String> modify(@Valid @RequestBody ${entity} ${entity ? uncap_first}) {
        ${table.serviceName ? uncap_first}.updateById(${entity ? uncap_first});
        return ResponseResult.success();
    }

    @ApiOperation(value = "查询活动详情")
    @GetMapping("/service/${package.ModuleName}/detail")
    public ResponseResult<${entity}> detail(@RequestParam Integer id) {
        return ResponseResult.success(${table.serviceName ? uncap_first}.getById(id));
    }

    @ApiOperation(value = "分页查询")
    @PostMapping("/service/${package.ModuleName}/queryByPage")
    public ResponseResult<IPage<${entity}>> queryByPage(@RequestBody JSONObject queryObject) {

         SmartWrapper<${entity}> smartWrapper = new SmartWrapper<>();
         smartWrapper.parse(queryObject, ${entity}.class);

         QueryWrapper<${entity}> queryWrapper = smartWrapper.getQueryWrapper();
         IPage<${entity}> page = smartWrapper.generatePage();
         ${table.serviceName ? uncap_first}.page(page, queryWrapper);
         return ResponseResult.success(page);
    }

}
