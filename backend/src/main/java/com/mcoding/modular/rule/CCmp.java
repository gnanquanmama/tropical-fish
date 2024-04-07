package com.mcoding.modular.rule;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * @author wzt
 * @since 2022/11/30
 */
@Component("c")
public class CCmp extends NodeComponent {

    @Override
    public void process() {
        //do your business
        System.out.println("fuck C");
    }
}
