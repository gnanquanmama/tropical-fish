package com.mcoding.modular.generatecode.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mcoding.modular.generatecode.entity.BaseGenerateCode;

/**
 * <p>
 * 大套餐活动 服务类
 * </p>
 *
 * @author wzt
 * @since 2020-02-08
 */
public interface BaseGenerateCodeService extends IService<BaseGenerateCode> {

    /**
     * 获取编码
     *
     * @param targetCode
     * @return
     */
    String generateNextCode(String targetCode);

}

