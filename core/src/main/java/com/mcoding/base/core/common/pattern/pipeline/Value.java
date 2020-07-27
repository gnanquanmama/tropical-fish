package com.mcoding.base.core.common.pattern.pipeline;

/**
 * @author wzt on 2020/5/4.
 * @version 1.0
 */
public abstract class Value<T> {

    public Value<T> next;

    public Value<T> getNext() {
        return next;
    }

    public void setNext(Value<T> v) {
        this.next = v;
    }

    public abstract void invoke(T s);
}
