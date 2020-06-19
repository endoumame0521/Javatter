package org.javatter.javatter.config;

import org.javatter.javatter.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // @Bean使用するので@Configuration必要
@EnableWebSecurity // Thymeleafを共に使うことで、formの中にCSRFのトークンが自動で埋め込まれるように
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    // セキュリティ設定を無視するリクエスト設定
    // 静的リソース(images、css、javascript)に対するアクセスはセキュリティ設定を無視する
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/css/**", "/js/**", "images/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests() // 以下は認証についての各種設定
                .antMatchers("/login").permitAll() // loginページは認証不要
                // .antMatchers("/users/**").permitAll() // ユーザー一覧ページ認証不要
                .anyRequest().authenticated(); // それ以外のページは認証必要
        httpSecurity.formLogin() // 以下はログインについての各種設定
                .loginPage("/login") // ログインページのURL
                .defaultSuccessUrl("/users") // ログイン成功時の遷移先URL
                // .loginProcessingUrl("/sign_in") // ログイン処理をするURL
                .failureUrl("/login?error") // ログイン失敗時のURL
                // .successForwardUrl("/users") // ログイン成功時のフォワード
                .usernameParameter("email") // ユーザーのパラメーター名
                .passwordParameter("password"); // パスワードのパラメーター名
        // .permitAll();
        httpSecurity.logout() // 以下はログアウトについての各種設定
                .logoutUrl("/logout") // ログアウト処理をするURL
                .logoutSuccessUrl("/logout?logout"); // ログアウト成功時のURL
        // .permitAll();
    }

    // パスワードのハッシュ化でBCrypt(Blowfish暗号)を使うことができるように
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // インジェクションされたUserDetailsServiceImplを認証プロセスに渡して認証している
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }
}
