package com.mcoding.base.core.orm;

import java.util.HashSet;
import java.util.Set;

/**
 * 查询关键字
 *
 * @author wzt
 */
public class QueryKeyWord {

    public static Set<String> queryKeyWord = new HashSet<>();

    static {
        queryKeyWord.add("current");
        queryKeyWord.add("size");
        queryKeyWord.add("orderByDesc");
        queryKeyWord.add("searchKeyword");
    }

}
