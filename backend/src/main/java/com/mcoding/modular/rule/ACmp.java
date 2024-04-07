package com.mcoding.modular.rule;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * @author wzt
 * @since 2022/11/30
 */
@Component("a")
public class ACmp extends NodeComponent {

    @Override
    public void process() {
        System.out.println("fuck A");
        //do your business
    }
}
