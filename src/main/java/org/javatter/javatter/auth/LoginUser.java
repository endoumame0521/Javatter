package org.javatter.javatter.auth;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.javatter.javatter.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;

// Serializableの実装によるシリアルNo.の設定を無視する設定
@SuppressWarnings("serial")
@Getter
public class LoginUser extends org.springframework.security.core.userdetails.User {
    private User user;

    // コンストラクタ呼び出し時にUser(Entity)のデータを元にuserdetails.Userを作成
    public LoginUser(User user) {
        super(user.getEmail(), user.getEncryptedPassword(), user.getStatus(), true, true, true,
                convertGrantedAuthorities(user.getRoles()));
        this.user = user;
    }

    // DBのrolesテーブルから取得してきたカンマ区切りの文字列を変換するメソッドを定義
    static Set<GrantedAuthority> convertGrantedAuthorities(String roles) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptySet(); // 空のセットを返す
        }

        // @formatter:off
        // rolesをカンマ区切りで取り出してSimpleGrantedAuthorities型インスタンスでSetにそれぞれ格納
        Set<GrantedAuthority> authorities = Stream.of(roles.split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
        // @formatter:on
        return authorities;
    }
}
