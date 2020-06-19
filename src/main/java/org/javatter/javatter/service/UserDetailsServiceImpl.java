package org.javatter.javatter.service;

import java.util.ArrayList;
import java.util.List;

import org.javatter.javatter.entity.LoginUser;
import org.javatter.javatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// ユーザー認証時に使用するサービスを定義
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LoginUser loginUser = userRepository.findByEmail(email);

        // DBからユーザーが見つからない場合は例外を発生
        if (loginUser == null) {
            throw new UsernameNotFoundException("メールアドレスが登録されていません");
        }

        // 権限のリスト
        // AdminやUserなどが存在するが、今回は利用しないのでUSERのみを仮で設定
        // 権限を利用する場合は、DB上で権限テーブル、ユーザ権限テーブルを作成し管理が必要
        List<GrantedAuthority> grantList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        grantList.add(authority);

        // UserDetailsのサブクラスであるUserクラスのコンストラクタに、
        // Eメール、暗号済パスワード、権限リストを渡して初期化している
        User user = new User(email, loginUser.getEncryptedPassword(), grantList);
        // UserDetailsクラスへアップキャストしてreturn
        UserDetails userDetails = (UserDetails) user;

        return userDetails;
    }
}
