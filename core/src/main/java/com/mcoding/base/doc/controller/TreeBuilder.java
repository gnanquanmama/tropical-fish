package com.mcoding.base.doc.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.mcoding.base.doc.EventNode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wzt on 2020/4/5.
 * @version 1.0
 */
public class TreeBuilder {


    public static EventNode build(List<EventNode> eventNodeList) {

        if (CollectionUtil.isEmpty(eventNodeList)) {
            return null;
        }

        Map<Long, List<EventNode>> parentGroupBy = eventNodeList.stream().collect(Collectors.groupingBy(EventNode::getParentId));

        // 根节点
        EventNode rootNode = eventNodeList.stream().filter(node -> 0 == node.getId()).findFirst().get();
        forEach(parentGroupBy, rootNode);

        return rootNode;
    }

    private static void forEach(Map<Long, List<EventNode>> parentIdGroupBy, EventNode eventNode) {
        List<EventNode> treeMenuNodes = parentIdGroupBy.get(eventNode.getId());
        if (parentIdGroupBy.get(eventNode.getId()) != null) {
            eventNode.setChildList(treeMenuNodes);
            eventNode.getChildList().forEach(t -> {
                forEach(parentIdGroupBy, t);
            });
        }
    }


}
