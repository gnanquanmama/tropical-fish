package com.mcoding.common.util;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author wzt on 2019/11/22.
 * @version 1.0
 */
@Data
@Builder
public class PageView<T> {

    private int current;
    private int size;
    private long total;

    private List<T> records;


    public static <T> PageView<T> newPageView() {
        return PageView.<T>builder().build();
    }

    public static <T> PageView<T> newPageView(int current, int size, long total) {
        return PageView.<T>builder().current(current).size(size).total(total).build();
    }

}
