package com.mcoding.common.util.mybatisplus;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Objects;

/**
 * @author wzt on 2020/2/12.
 * @version 1.0
 */
public class SmartPages {

    public static <E> IPage<E> create(JSONObject queryObject) {
        if (Objects.isNull(queryObject)) {
            return new Page<>(1, 10);
        }

        Object current = queryObject.get("current");
        Object size = queryObject.get("size");

        if (current == null || size == null) {
            return new Page<>(1, 10);
        }

        return new Page<>((int) current, (int) size);
    }
}
