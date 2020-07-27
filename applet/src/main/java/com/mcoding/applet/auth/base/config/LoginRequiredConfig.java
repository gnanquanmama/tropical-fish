package com.mcoding.applet.auth.base.config;

import com.mcoding.applet.auth.base.LoginRequiredArgumentResolver;
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
