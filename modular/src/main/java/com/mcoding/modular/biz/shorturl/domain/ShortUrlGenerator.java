package com.mcoding.modular.biz.shorturl.domain;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wzt on 2020/7/13.
 * @version 1.0
 */
@Component
public class ShortUrlGenerator {

    @Resource
    private RedissonClient redissonClient;


    private String[] chars = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",

            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z",

            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    public String generateShortUrl(String longUrl) {
        String shortUrl = shortUrl();

        this.saveShortUrlMap(shortUrl, longUrl);

        return shortUrl;
    }

    public String mapToShortUrl(String shortUrl) {
        return this.getLongUrl(shortUrl);
    }

    private String shortUrl() {
        Long shortUrlSeed = this.shortUrlSeed();

        Long urlSeed = 87622772882L + shortUrlSeed;

        StringBuilder stringBuilder = new StringBuilder();
        while (urlSeed > 0) {
            long index = urlSeed % 47;
            urlSeed = urlSeed / 47;

            stringBuilder.append(chars[(int) index]);
        }

        return stringBuilder.toString();
    }

    private Long shortUrlSeed() {
        RAtomicLong rAtomicLong = this.redissonClient.getAtomicLong("fish::short_url::short_url_seed");
        return rAtomicLong.incrementAndGet();
    }

    private void saveShortUrlMap(String shortUrl, String longUrl) {
        RMap<String, String> rMap = this.redissonClient.getMap("fish::short_url::short_url_map");
        rMap.put(shortUrl, longUrl);
    }

    private String getLongUrl(String shortUrl) {
        RMap<String, String> rMap = this.redissonClient.getMap("fish::short_url::short_url_map");
        return rMap.get(shortUrl);
    }


}
