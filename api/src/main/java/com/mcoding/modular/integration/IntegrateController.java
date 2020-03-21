package com.mcoding.modular.integration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mcoding.common.util.ResponseResult;
import com.mcoding.modular.dubbo.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author wzt on 2020/3/20.
 * @version 1.0
 */
@Slf4j
@Api(tags = "集成服务")
@RestController
public class IntegrateController {

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}",

            registry = "${dubbo.registry.id}",
            check = false,
            interfaceClass = DemoService.class)
    private DemoService demoService;

    @ApiOperation("zipkin集成测试")
    @GetMapping("/service/dubbo/getUserName")
    public ResponseResult<String> testMethod(String userName) {
        return ResponseResult.success(demoService.sayHello(userName));
    }

    @Resource
    private RedissonClient redissonClient;

    @ApiOperation("分布式锁测试")
    @PostMapping("/service/lock/getLock")
    public ResponseResult<String> getLock(String lockName) {
        RLock lock = redissonClient.getLock(lockName);
        try {
            boolean locked = lock.tryLock(2, 10, TimeUnit.SECONDS);
            if (locked) {
                log.info("EVENT=获取分布式锁|desc=success");
            } else {
                log.warn("EVENT=获取分布式锁|desc=fail");
            }

        } catch (InterruptedException i) {
            i.printStackTrace();
        }

        return ResponseResult.success();
    }


}
