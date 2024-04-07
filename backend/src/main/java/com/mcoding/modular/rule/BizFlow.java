package com.mcoding.modular.rule;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wzt
 * @since 2022/11/30
 */
@Slf4j
@Component
public class BizFlow {

    @Resource
    private FlowExecutor flowExecutor;

    public void execute() {

        flowExecutor.reloadRule();
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
        DefaultContext context = response.getFirstContextBean();
        System.out.println(JsonUtil.toJsonString(context.getData("student")));
        if (response.isSuccess()){
            log.info("执行成功");
        }else{
            log.info("执行失败");
        }

        String code = response.getCode();
        String message = response.getMessage();

        System.out.println(String.format("%s %s", code, message));
    }

}
