package org.javatter.javatter.service;

import java.time.LocalDate;
import java.util.List;

import org.javatter.javatter.entity.User;
import org.javatter.javatter.form.UserForm;
import org.javatter.javatter.form.UserUpdateForm;
import org.javatter.javatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        // ユーザーをデータベースから全件取得
        return userRepository.findAll();
    }

    @Transactional
    public void createUser(UserForm userForm) {
        User user = new User();
        // フォームから取得した各データをエンティティにセットし、最後にDBへ保存
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setBirthday(getLocalDate(userForm));
        user.setEncryptedPassword(userForm.getEncryptedPassword());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        // ユーザーをデータベースから１件だけ取得
        return userRepository.findById(id).get();
    }

    @Transactional
    public void updateUser(Long id, UserUpdateForm userUpdateForm) {
        // IDを元にDBから1つのユーザーを取得
        User user = getUser(id);
        // フォームから取得した各データをエンティティにセット
        user.setName(userUpdateForm.getName());
        // UserForm userForm = userUpdateForm;
        user.setBirthday(getLocalDate(userUpdateForm));
        user.setEmail(userUpdateForm.getEmail());
        user.setEncryptedPassword(userUpdateForm.getEncryptedPassword());
        user.setProfileImage(userUpdateForm.getProfileImage());
        user.setBackgroundImage(userUpdateForm.getBackgroundImage());
        user.setIntroduction(userUpdateForm.getIntroduction());
        user.setAddress(userUpdateForm.getAddress());
        user.setWebSite(userUpdateForm.getWebSite());
        user.setStatus(userUpdateForm.getStatus());
        // エンティティにセットされたデータをDBへ保存
        userRepository.save(user);
    }

    private LocalDate getLocalDate(UserForm userForm) {
        // フォームから年月日をそれぞれ取得してLocalDate型に変換
        int birthYear = userForm.getBirthYear();
        int birthMonth = userForm.getBirthMonth();
        int birthDay = userForm.getBirthDay();
        LocalDate birthDate = LocalDate.of(birthYear, birthMonth, birthDay);
        return birthDate;
    }
}
