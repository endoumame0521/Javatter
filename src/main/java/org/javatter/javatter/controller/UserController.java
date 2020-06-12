package org.javatter.javatter.controller;

import java.util.List;

import org.javatter.javatter.entity.User;
import org.javatter.javatter.form.UserForm;
import org.javatter.javatter.form.UserUpdateForm;
import org.javatter.javatter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String index(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "users/index";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "users/new";
    }

    @PostMapping("/users")
    public String create(UserForm userForm) {
        userService.createUser(userForm);
        return "redirect:/users";
    }

    @GetMapping("/users/{id}")
    public String show(@PathVariable Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "users/show";
    }

    @GetMapping("/users/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        User user = userService.getUser(id);

        UserUpdateForm userUpdateForm = new UserUpdateForm();
        userUpdateForm.setId(id);
        userUpdateForm.setName(user.getName());
        userUpdateForm.setEmail(user.getEmail());
        userUpdateForm.setBirthYear(user.getBirthday().getYear());
        userUpdateForm.setBirthMonth(user.getBirthday().getMonthValue());
        userUpdateForm.setBirthDay(user.getBirthday().getDayOfMonth());
        userUpdateForm.setEncryptedPassword(user.getEncryptedPassword());
        userUpdateForm.setProfileImage(user.getProfileImage());
        userUpdateForm.setBackgroundImage(user.getBackgroundImage());
        userUpdateForm.setIntroduction(user.getIntroduction());
        userUpdateForm.setAddress(user.getAddress());
        userUpdateForm.setWebSite(user.getWebSite());
        userUpdateForm.setStatus(user.getStatus());
        model.addAttribute("userUpdateForm", userUpdateForm);
        return "users/edit";
    }

    @PutMapping("/users/{id}")
    public String update(@PathVariable Long id, UserUpdateForm userUpdateForm) {
        userService.updateUser(id, userUpdateForm);
        return String.format("redirect:/users/%d", userUpdateForm.getId());
    }
}
