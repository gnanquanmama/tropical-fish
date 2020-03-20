package com.mcoding.modular.lock;

import com.mcoding.common.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author wzt on 2020/3/20.
 * @version 1.0
 */
@Api(tags = "分布式锁服务")
@Slf4j
@RestController
public class DistributedLockController {

    @Resource
    private RedissonClient redissonClient;

    @ApiOperation("获取锁")
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
