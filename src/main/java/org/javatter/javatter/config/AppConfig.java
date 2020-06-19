package org.javatter.javatter.config;

import org.javatter.javatter.converter.UserConverter;
import org.modelmapper.ModelMapper;
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
}
