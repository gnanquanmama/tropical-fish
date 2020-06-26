package com.mcoding.modular.generatecode.domain;

import com.mcoding.modular.generatecode.service.BaseGenerateCodeService;
import lombok.Setter;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 业务编码生成器抽象类
 *
 * @author wzt on 2020/6/26.
 * @version 1.0
 */
public abstract class AbstractBizCodeGenerator {

    private static LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();

    @Resource
    private BaseGenerateCodeService baseGenerateCodeService;

    @Setter
    private String targetCode;

    /**
     * 默认缓存 10 个业务编码
     */
    @Setter
    private int cacheQuantity = 10;

    /**
     * 生成下一个业务编码，默认缓存10个
     *
     * @return
     */
    public String generateNextCode() {

        if (blockingQueue.size() > 0) {
            return blockingQueue.poll();
        }

        synchronized (this) {
            if (blockingQueue.size() > 0) {
                return blockingQueue.poll();
            }

            // 缓存数量最大设置为不超过 100
            cacheQuantity = cacheQuantity > 100 ? 100 : cacheQuantity;

            List<String> codeList = baseGenerateCodeService.generateBizCodeList(targetCode, cacheQuantity);
            for (String bizCode : codeList) {
                blockingQueue.offer(bizCode);
            }

            return blockingQueue.poll();
        }
    }

}
