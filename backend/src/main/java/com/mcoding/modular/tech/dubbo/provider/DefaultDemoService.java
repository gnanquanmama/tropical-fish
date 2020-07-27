package com.mcoding.modular.tech.dubbo.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.mcoding.modular.tech.dubbo.DemoService;

/**
 * @author wzt on 2020/3/20.
 * @version 1.0
 */
@Service(version = "1.0.0")
public class DefaultDemoService implements DemoService {

    @Override
    public String sayHello(String name) {
        return String.format("[dubbo-rpc] : Hello, %s", name);
    }

}
