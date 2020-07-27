package com.mcoding.base.common.pattern.pipeline;

/**
 * @author wzt on 2020/5/4.
 * @version 1.0
 */
public class StandardPipeline<T> {

    protected Value<T> head;
    protected Value<T> tail;

    public Value<T> getHead() {
        return this.head;
    }

    public Value<T> getTail() {
        return this.tail;
    }

    public void setTail(Value<T> v) {
        this.tail = v;
    }

    public void addValue(Value<T> v) {
        if (head == null) {
            head = v;
            v.setNext(tail);
            return;
        }

        Value<T> current = head;
        while (current != null) {
            if (current.getNext() == tail) {
                current.setNext(v);
                v.setNext(tail);
                break;
            }
            current = current.getNext();
        }
    }
}
