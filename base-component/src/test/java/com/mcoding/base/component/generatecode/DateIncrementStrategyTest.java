package com.mcoding.base.component.generatecode;

import com.mcoding.base.component.ComponentApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author wzt on 2020/7/9.
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComponentApplication.class)
public class DateIncrementStrategyTest {

    @Resource
    private strategy.DateIncrementStrategy dateIncrementStrategy;

    @MockBean
    private com.mcoding.modular.biz.generatecode.entity.BaseGenerateCode generateCode;

    @Test
    public void test() {

        Mockito.when(generateCode.getCurrentCode()).thenReturn("202007090000000001");
        Mockito.when(generateCode.getMaxCode()).thenReturn("9999999999");

        int quantity = 10;
        String lastCode = this.dateIncrementStrategy.generateListCode(generateCode, quantity);

        Assert.assertEquals("202007090000000011", lastCode);
    }


}
