package org.javatter.javatter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    HandlerInterceptor redirectNotCurrentUserInterceptor;

    // interceptorを登録
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(redirectNotCurrentUserInterceptor);
    }

    // application.propertiesの値を取得
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String PROPERTY_DDL_AUTO;

    // application.prppertiesのddl-autoがcreateの場合のみDBへの初期データ投入を実施
    @Bean
    public InsertInitialData InsertInitialData() {
        return ("create".equals(this.PROPERTY_DDL_AUTO)) ? new InsertInitialData() : null;
    }
}
