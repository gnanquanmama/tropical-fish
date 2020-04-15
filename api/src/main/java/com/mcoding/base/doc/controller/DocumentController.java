package com.mcoding.base.doc.controller;

import com.mcoding.base.doc.EventNode;
import com.mcoding.base.doc.EventNodeContainer;
import com.mcoding.common.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author wzt on 2020/3/26.
 * @version 1.0
 */
@Api(tags = "基础-文档服务")
@RestController
public class DocumentController {


    @ApiOperation("根据traceID查看方法调用树视图")
    @GetMapping("/service/doc/viewInvokeTree")
    public ResponseResult<EventNode> view(String traceId) {
        List<EventNode> nodeList = EventNodeContainer.get(traceId);
        EventNode rootNodeReference = TreeBuilder.build(nodeList);

        return ResponseResult.success(rootNodeReference);
    }

    @ApiOperation("查看所有方法调用树的TraceId")
    @GetMapping("/service/doc/viewInvokeTreeAllTraceId")
    public ResponseResult<Set<String>> viewALLTraceId() {
        Set<String> traceIdSet = EventNodeContainer.getAllTraceId();
        return ResponseResult.success(traceIdSet);
    }

}
