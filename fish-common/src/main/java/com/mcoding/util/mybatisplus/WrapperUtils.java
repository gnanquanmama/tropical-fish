package com.mcoding.util.mybatisplus;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.joor.Reflect;

import java.util.Objects;
import java.util.Set;

public class WrapperUtils {


    public static <T> QueryWrapper<T> createWrapper(JSONObject queryObject){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if(Objects.isNull(queryObject)){
            return queryWrapper;
        }

        Set<String> keySet = queryObject.keySet();
        for(String key: keySet){
            System.out.println(key);

            String[] fieldNameAndOperation =  key.split("_\\$_");
            String fieldName = fieldNameAndOperation[0];
            String operation = fieldNameAndOperation[1];

            Object value = queryObject.get(key);
            Reflect.on(queryWrapper).call(operation,fieldName,value);
        }

        return queryWrapper;
    }

}
