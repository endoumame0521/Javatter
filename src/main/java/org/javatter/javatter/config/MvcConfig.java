package org.javatter.javatter.config;

import org.javatter.javatter.interceptor.RedirectNotCurrentUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    HandlerInterceptor redirectNotCurrentUserInterceptor;

    // interceptorをBeanに登録
    @Bean
    public HandlerInterceptor redirectNotCurrentUserInterceptor() throws Exception {
        return new RedirectNotCurrentUserInterceptor();
    }

    // interceptorを追加
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(redirectNotCurrentUserInterceptor);
    }
}
