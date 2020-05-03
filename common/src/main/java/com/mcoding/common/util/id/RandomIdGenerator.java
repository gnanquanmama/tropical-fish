package com.mcoding.common.util.id;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.Date;
import java.util.Random;

/**
 * @author wzt on 2020/3/9.
 * @version 1.0
 */
@Slf4j
public class RandomIdGenerator implements IdGenerator {

    @Override
    public String generate() {
        String dateTime = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        int lastFieldOfAddress = getLastfieldOfAddress();
        String randomAlphameric = generateRandomAlphameric(8);

        return String.format("%s-%03d%s", dateTime, lastFieldOfAddress, randomAlphameric);
    }

    private int getLastfieldOfAddress() {
        int lastFieldOfAddress = 0;
        try {
            String getHostAddress = InetAddress.getLocalHost().getHostAddress();
            String[] tokens = getHostAddress.split("\\.");
            String lastFieldOfAddressStr = tokens[tokens.length - 1];
            return Integer.valueOf(lastFieldOfAddressStr);
        } catch (Exception e) {
            log.warn("Failed to get the host name.", e);
        }
        return lastFieldOfAddress;
    }

    private String generateRandomAlphameric(int length) {
        char[] randomChars = new char[length];
        int count = 0;
        Random random = new Random();
        while (count < length) {
            int maxAscii = 'z';
            int randomAscii = random.nextInt(maxAscii);
            boolean isDigit = randomAscii >= '0' && randomAscii <= '9';
            boolean isUppercase = randomAscii >= 'A' && randomAscii <= 'Z';
            boolean isLowercase = randomAscii >= 'a' && randomAscii <= 'z';
            if (isDigit || isUppercase || isLowercase) {
                randomChars[count] = (char) (randomAscii);
                ++count;
            }
        }
        return new String(randomChars);
    }

}