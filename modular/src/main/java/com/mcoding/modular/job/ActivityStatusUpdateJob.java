package com.mcoding.modular.job;

import com.mcoding.base.log.MdcLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wzt on 2020/2/9.
 * @version 1.0
 */
@Slf4j
@Component
public class ActivityStatusUpdateJob {


    @MdcLog
    @XxlJob(value = "activityStatusUpdateJob")
    public ReturnT<String> execute(String s) {
        try {
            // do the job
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("event=更新大套餐活动状态[异常]|{}", e.getMessage());
            return ReturnT.FAIL;
        }
    }


}
