package com.mcoding.modular.generatecode.strategy;


import com.mcoding.modular.generatecode.entity.BaseGenerateCode;

/**
 * 编码生成策略
 *
 * @author hzy
 */
public abstract class GenerateStrategy {

    /**
     * 根据当前编码生成下一个新编码
     *
     * @param currentCode 当前编码已经编码参数
     * @return
     */
    public abstract String generateNextCode(BaseGenerateCode currentCode);
}
