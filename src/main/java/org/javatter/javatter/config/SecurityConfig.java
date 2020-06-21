package org.javatter.javatter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // @Bean使用するので@Configuration必要
@EnableWebSecurity // Thymeleafを共に使うことで、formの中にCSRFのトークンが自動で埋め込まれるように
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // セキュリティ設定を無視するリクエスト設定
    // 静的リソース(images、css、javascript)に対するアクセスはセキュリティ設定を無視する
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        // @formatter:off
        webSecurity
            .debug(false)
            .ignoring()
            .antMatchers("/css/**", "/js/**", "images/**");
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // @formatter:off
        httpSecurity
            .authorizeRequests() // 認証に関する設定
                .mvcMatchers("/users").permitAll() // 未認証でも閲覧可能なURL
                .mvcMatchers("/users/new").permitAll() // 未認証でも閲覧可能なURL
                .mvcMatchers(HttpMethod.GET, "/users/{id}").permitAll() // GETのみ未認証でも閲覧可能
                .mvcMatchers("/users/**").hasRole("USER") // USER権限で閲覧可能なURL
                .mvcMatchers("/admin/**").hasRole("ADMIN") // ADMIN権限で閲覧可能なURL
                .anyRequest().authenticated()
            .and()
            .formLogin() // ログイン後の設定
                .defaultSuccessUrl("/users") // リダイレクト先
            .and()
            .logout() // ログアウト後の設定
                .invalidateHttpSession(true) // httpセッション無効
                .deleteCookies("JSESSIONID") //　JSESSIONIDというクッキーを削除
                .logoutSuccessUrl("/users"); // リダイレクト先
        // @formatter:on
    }

    // パスワードのハッシュ化でBCrypt(Blowfish暗号)を使うことができるように
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
