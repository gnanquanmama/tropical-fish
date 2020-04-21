package com.mcoding.base.doc;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wzt on 2020/4/2.
 * @version 1.0
 */

public class EventNodeContainer {

    private static Cache<String, List<EventNode>> container = CacheBuilder
            .newBuilder()
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build();

    static void put(String traceId, EventNode eventNode) {
        List<EventNode> eventNodeList = container.getIfPresent(traceId);
        if (CollectionUtil.isEmpty(eventNodeList)) {
            container.put(traceId, Lists.newArrayList(eventNode));
        } else {
            eventNodeList.add(eventNode);
        }
    }

    public static List<EventNode> get(String traceId) {
        return container.getIfPresent(traceId);
    }

    public static Set<String> getAllTraceId() {
        return container.asMap().keySet();
    }

}
