package com.mcoding.modular.generatecode.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mcoding.modular.generatecode.entity.BaseGenerateCode;

import java.util.List;

/**
 * <p>
 * 业务编码 服务类
 * </p>
 *
 * @author wzt
 * @since 2020-02-08
 */
public interface BaseGenerateCodeService extends IService<BaseGenerateCode> {

    /**
     * 生成业务编码列表
     *
     * @param targetCode
     * @param quantity
     * @return
     */
    List<String> generateBizCodeList(String targetCode, int quantity);

}

