package com.mcoding.base.common.pattern.pipeline;

/**
 * @author wzt on 2020/5/4.
 * @version 1.0
 */
public interface Pipeline<T> {

    Value getHead();

    Value getTail();

    void setTail(Value<T> v);

    void addValue(Value<T> v);

}
