package com.mcoding.base.component.generatecode.domain;

import com.google.common.collect.Range;
import com.mcoding.base.component.generatecode.service.BaseGenerateCodeService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 通用业务编码生成器
 *
 * @author wzt on 2020/6/26.
 * @version 1.0
 */
@Slf4j
public class CommonBizCodeGenerator {

    private Queue<String> bizCodeQueue = new LinkedList<>();

    @Resource
    private BaseGenerateCodeService baseGenerateCodeService;

    @Setter
    private String targetCode;

    /**
     * 默认缓存 10 个业务编码
     */
    @Setter
    private int cacheQuantity = 10;

    private static final int MAX_CACHE_QUANTITY = 100;

    /**
     * 生成下一个业务编码，默认缓存10个
     *
     * @return
     */
    public synchronized String generateNextCode() {

        if (bizCodeQueue.size() > 0) {
            return bizCodeQueue.poll();
        }

        // 缓存数量最大设置为不超过 100
        if (!Range.closed(1, MAX_CACHE_QUANTITY).contains(cacheQuantity)) {
            cacheQuantity = 100;
        }

        List<String> codeList = baseGenerateCodeService.generateBizCodeList(targetCode, cacheQuantity);

        for (String bizCode : codeList) {
            bizCodeQueue.offer(bizCode);
        }

        return bizCodeQueue.poll();
    }

    /**
     * 批量生成业务编码
     *
     * @param quantity 数量
     * @return
     */
    public List<String> generateBizCodeList(int quantity) {
        return baseGenerateCodeService.generateBizCodeList(targetCode, quantity);
    }

}
