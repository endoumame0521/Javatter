package org.javatter.javatter.converter;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.javatter.javatter.entity.User;
import org.javatter.javatter.form.UserForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ユーザー登録時のフォームからエンテティへの詰め替え処理
    public void formToEntityAtCreate(UserForm userForm, User user) {
        modelMapper.map(userForm, user);
        user.setBirthday(joinBirthday(userForm));
        user.setEncryptedPassword(encodePassword(userForm));
        user.setRoles(joinRoles(userForm));
    }

    // フォームからエンテティへの詰め替え処理
    public void formToEntity(UserForm userForm, User user) {
        modelMapper.map(userForm, user);
        user.setBirthday(joinBirthday(userForm));
    }

    // エンティティからフォームへの詰め替え処理
    public void entityToForm(User user, UserForm userForm) {
        modelMapper.map(user, userForm);
        userForm.setBirthY(user.getBirthday().getYear());
        userForm.setBirthM(user.getBirthday().getMonthValue());
        userForm.setBirthD(user.getBirthday().getDayOfMonth());
    }

    // 生年月日を連結してDateTime型に変換
    private LocalDate joinBirthday(UserForm userForm) {
        int birthY = userForm.getBirthY();
        int birthM = userForm.getBirthM();
        int birthD = userForm.getBirthD();
        return LocalDate.of(birthY, birthM, birthD);
    }

    // 生のパスワードを暗号化
    private String encodePassword(UserForm userForm) {
        String rawPassword = userForm.getRawPassword();
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        return encryptedPassword;
    }

    // roles配列のデータを全て取り出してカンマ区切りにして新しい文字列を返す
    private String joinRoles(UserForm userForm) {
        String[] roles = userForm.getRoles();
        if (roles == null || roles.length == 0) {
            return "";
        }
        // @formatter:off
        return Stream.of(roles)
        .map(String::trim)
        .map(String::toUpperCase)
        .collect(Collectors.joining(","));
        // @formatter:on
    }
}
