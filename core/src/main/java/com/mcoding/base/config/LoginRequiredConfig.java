package com.mcoding.base.config;

import com.mcoding.base.auth.LoginRequiredArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author wzt on 2020/6/13.
 * @version 1.0
 */
public class LoginRequiredConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List resolvers) {
        resolvers.add(new LoginRequiredArgumentResolver());
    }

}
