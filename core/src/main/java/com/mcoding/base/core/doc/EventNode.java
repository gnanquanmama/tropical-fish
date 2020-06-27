package com.mcoding.base.core.doc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * 事件节点
 *
 * @author wzt on 2020/4/2.
 * @version 1.0
 */
@Data
public class EventNode {

    /**
     * 调用树节点ID
     */
    private long id;
    /**
     * 调用链路标志
     */
    @JsonIgnore
    private String traceId;

    /**
     * 当前节点的父节点ID
     */
    private long parentId;

    /**
     * 当前方法在java源码中的行数
     */
    private int lineNum;

    /**
     * 当前方法名称
     */
    private String method;

    /**
     * 触发事件
     */
    private String event;

    private String lifeCycle;

    /**
     * 方法是否同步,默认为同步
     */
    private boolean sync;

    /**
     * 子节点列表
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<EventNode> childList;

}
