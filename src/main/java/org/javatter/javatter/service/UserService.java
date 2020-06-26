package org.javatter.javatter.service;

import java.util.List;

import org.javatter.javatter.converter.UserConverter;
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

    @Autowired
    private UserConverter userConverter;

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        // ユーザーをデータベースから全件取得
        return userRepository.findAll();
    }

    @Transactional
    public void createUser(UserForm userForm) {
        User user = new User();
        // フォームから取得した各データをエンティティにセットし、最後にDBへ保存
        userConverter.formToEntityAtCreate(userForm, user);
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
        userConverter.formToEntity(userUpdateForm, user);
        // エンティティにセットされたデータをDBへ保存
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
