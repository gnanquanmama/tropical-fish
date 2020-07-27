package com.mcoding.base.core.common.util.collection;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author wzt on 2020/1/16.
 * @version 1.0
 */
public class MapUtils {

    /**
     * 对每个分组求和
     *
     * @param sourceMap
     * @param function
     * @param <S>
     * @param <R>
     * @return
     */
    public static <S, R> Map<String, BigDecimal> sumEachGroupList(Map<String, List<S>> sourceMap, Function<S, R> function) {

        Map<String, BigDecimal> resultMap = new HashMap<>(sourceMap.size());

        sourceMap.forEach((key, list) -> {
            BigDecimal sum = BigDecimal.ZERO;
            for (S t : list) {
                R result = function.apply(t);
                sum = sum.add(new BigDecimal(result.toString()));
            }
            resultMap.put(key, sum);
        });

        return resultMap;
    }

}
