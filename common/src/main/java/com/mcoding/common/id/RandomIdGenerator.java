package com.mcoding.common.id;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
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
        String lastfieldOfHostName = getLastfieldOfHostName();
        long threadId = Thread.currentThread().getId();
        String dateTime = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        String randomAlphameric = generateRandomAlphameric(8);

        return String.format("%s-%d-%s-%s", lastfieldOfHostName, threadId, dateTime, randomAlphameric);
    }

    private String getLastfieldOfHostName() {
        String substrOfHostName = "";
        try {
            String getHostAddress = InetAddress.getLocalHost().getHostAddress();
            String[] tokens = getHostAddress.split("\\.");
            substrOfHostName = tokens[tokens.length - 1];
            return substrOfHostName;
        } catch (UnknownHostException e) {
            log.warn("Failed to get the host name.", e);
        }
        return substrOfHostName;
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