package com.mcoding.modular.biz.shorturl.controller;


import com.mcoding.base.core.rest.ResponseResult;
import com.mcoding.modular.biz.shorturl.domain.ShortUrlGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 基础用户
 * </p>
 *
 * @author wzt
 * @since 2020-06-21
 */
@Slf4j
@Api(tags = "基础-短链接服务")
@RestController
public class ShortUrlController {

    @Resource
    private ShortUrlGenerator shortUrlGenerator;

    @ApiOperation("生成短链接")
    @PostMapping("/service/shortUrl/generateShortUrl")
    public ResponseResult<String> generateShortUrl(@RequestParam String longUrl) {

        return ResponseResult.success(shortUrlGenerator.generateShortUrl(longUrl));
    }

    @ApiOperation("映射为长链接")
    @PostMapping("/service/shortUrl/mapToLongUrl")
    public ResponseResult<String> mapToLongUrl(@RequestParam String shortUrl) {

        return ResponseResult.success(shortUrlGenerator.mapToShortUrl(shortUrl));
    }



}
