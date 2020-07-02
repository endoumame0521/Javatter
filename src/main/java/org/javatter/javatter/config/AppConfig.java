package org.javatter.javatter.config;

import org.javatter.javatter.converter.FileEncoder;
import org.javatter.javatter.converter.UserConverter;
import org.javatter.javatter.initialize.InsertUserData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// Springにbeanとして登録して@Autowiredで使用できるようにする
// 同時にModelMapperの初期設定を定義
@Configuration
@ComponentScan("org.javatter")
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // コピー元がnullのカラムはコピー先に上書きしない設定を有効に
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        return modelMapper;
    }

    @Bean
    public UserConverter userConverter() {
        return new UserConverter();
    }

    @Bean
    public FileEncoder fileEncoder() {
        return new FileEncoder();
    }

    // application.propertiesの値を取得
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String PROPERTY_DDL_AUTO;

    // application.prppertiesのddl-autoがcreateの場合のみDBへの初期データ投入を実施
    @Bean
    public InsertUserData InsertInitialData() {
        return ("create".equals(this.PROPERTY_DDL_AUTO)) ? new InsertUserData() : null;
    }
}
