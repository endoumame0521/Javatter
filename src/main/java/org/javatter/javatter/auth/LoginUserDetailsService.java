package org.javatter.javatter.auth;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.javatter.javatter.form.UserForm;
import org.javatter.javatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// ユーザー認証時に使用するサービスを定義
@Service
public class LoginUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    // メールアドレスで検索したユーザーのUserエンティティをLoginUserクラスのインスタンスへ変換する
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).map(LoginUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Eメールが存在しません"));
    }

    // 手動でログインするメソッドを定義
    public void loginUser(HttpServletRequest request, UserForm userForm) {
        try {
            request.login(userForm.getEmail(), userForm.getRawPassword());
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
