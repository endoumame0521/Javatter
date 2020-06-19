package org.javatter.javatter.converter;

import java.time.LocalDate;

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

    // フォームからエンテティへの詰め替え処理
    public void formToEntity(UserForm userForm, User user) {
        modelMapper.map(userForm, user);

        // 生年月日を詰め替え
        int birthY = userForm.getBirthY();
        int birthM = userForm.getBirthM();
        int birthD = userForm.getBirthD();
        user.setBirthday(LocalDate.of(birthY, birthM, birthD));

        // 生のパスワードを暗号化してから詰め替え
        String rawPassword = userForm.getRawPassword();
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        user.setEncryptedPassword(encryptedPassword);
    }

    // エンティティからフォームへの詰め替え処理
    public void entityToForm(User user, UserForm userForm) {
        modelMapper.map(user, userForm);

        userForm.setBirthY(user.getBirthday().getYear());
        userForm.setBirthM(user.getBirthday().getMonthValue());
        userForm.setBirthD(user.getBirthday().getDayOfMonth());
    }
}
