package com.fastdev.util;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Objects;

public class QueryPageUtils {

    public static <E> Page<E> createPage(JSONObject queryObject){
        if(Objects.isNull(queryObject)){
            return new Page<>(1, 10);
        }

        Object current = queryObject.get("current");
        Object size = queryObject.get("size");

        if(current == null || size == null){
            return new Page<>(1, 10);
        }

        return new Page<>((int)current, (int)size);
    }
}
