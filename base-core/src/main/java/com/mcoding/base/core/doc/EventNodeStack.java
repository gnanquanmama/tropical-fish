package com.mcoding.base.core.doc;

import cn.hutool.core.collection.CollectionUtil;

import java.util.Stack;

/**
 * @author wzt on 2020/4/2.
 * @version 1.0
 */
public class EventNodeStack {

    private static ThreadLocal<Stack<EventNode>> threadLocal = new ThreadLocal<>();

    static void push(EventNode eventNode) {
        Stack<EventNode> stack = threadLocal.get();
        if (stack == null) {
            stack = new Stack<>();
        }
        stack.push(eventNode);
        threadLocal.set(stack);
    }

    static EventNode pop() {
        return threadLocal.get().pop();
    }

    static EventNode peek() {
        Stack<EventNode> stack = threadLocal.get();
        if (CollectionUtil.isEmpty(stack)) {
            return null;
        }
        return stack.peek();
    }

    static int size() {
        Stack<EventNode> stack = threadLocal.get();
        return stack == null ? 0 : stack.size();
    }

    public static void clear() {
        threadLocal.remove();
    }

}
