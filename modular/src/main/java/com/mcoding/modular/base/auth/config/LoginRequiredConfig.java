package com.mcoding.modular.base.auth.config;

import com.mcoding.modular.base.auth.LoginRequiredArgumentResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author wzt on 2020/6/13.
 * @version 1.0
 */
@Component
public class LoginRequiredConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List resolvers) {
        resolvers.add(new LoginRequiredArgumentResolver());
    }

}
