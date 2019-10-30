package com.mcoding.util.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzt on 2019/10/30.
 * @version 1.0
 */
public class RamPager<T> {

    private List<T> data;
    private int pageNum;
    private int pageSize;

    public RamPager(List<T> data, int pageNum, int pageSize) {
        this.data = data;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public List<T> page() {
        if (data == null || data.isEmpty()) {
            return new ArrayList<>();
        }
        if (pageNum < 0) {
            pageNum = 1;
        }
        int from = (pageNum - 1) * pageSize;
        int to = pageNum * pageSize;
        if (to > data.size()) {
            to = data.size();
        }
        if (from >= data.size() || to <= from) {
            return new ArrayList<>();
        }
        return data.subList(from, to);
    }
}
