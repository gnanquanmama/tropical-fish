package com.mcoding.base.core.common.util.math;


import cn.hutool.core.lang.Assert;

import java.math.BigDecimal;
import java.util.List;

/**
 * Utility to help comparison of {@link BigDecimal}.
 * <p>
 * The only way to compare {@link BigDecimal} is to get result of compare
 * function of {@link BigDecimal} and compare the result with -1, 0 and 1.
 * <p>
 * Although it is straight forward however it lacks expressiveness and decreases
 * readability. For instance look at this line of code :
 *
 * <pre>
 * <code>
 *     if(balance.compareTo(maxAmount) &lt; 0))
 * </code>
 * </pre>
 * <p>
 * the code above try to check condition "balance &lt; maxAmount". You
 * definitely spotted the problem. now imagine how hard it can be if you have to
 * read some code with a lot of {@link BigDecimal} comparison!!
 * {@link BigDecimalUtils} makes comparison of {@link BigDecimal}s more easier
 * and more readable than the comparator method. look how above code are written
 * by the help of this library.
 *
 * <pre>
 * <code>
 *     if( is(balance).lt(maxAmount) )
 * </code>
 * </pre>
 *
 * @author adigozalpour
 */
public final class BigDecimalUtils {


    public static BigDecimalWrapper is(BigDecimal decimal) {
        return new BigDecimalWrapper(decimal);
    }

    public static BigDecimalWrapper is(double decimal) {
        return is(BigDecimal.valueOf(decimal));
    }

    public static BigDecimal max(List<BigDecimal> bigDecimalList) {
        Assert.notEmpty(bigDecimalList, "bigDecimalList 不能为空");
        return bigDecimalList.stream().max(BigDecimal::compareTo).get();
    }

    public static BigDecimal min(List<BigDecimal> bigDecimalList) {
        Assert.notEmpty(bigDecimalList, "bigDecimalList 不能为空");
        return bigDecimalList.stream().min(BigDecimal::compareTo).get();
    }
}