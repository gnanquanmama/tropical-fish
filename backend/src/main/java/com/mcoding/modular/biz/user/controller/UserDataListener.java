package com.mcoding.modular.biz.user.controller;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.mcoding.base.user.entity.BaseUser;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2022/4/9.
 *
 * @author wzt
 */
@Slf4j
public class UserDataListener implements ReadListener<BaseUser> {

    @Override
    public void invoke(BaseUser baseUser, AnalysisContext analysisContext) {
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }
}
