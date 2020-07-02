package org.javatter.javatter.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.javatter.javatter.annotation.RedirectNotCurrentUser;
import org.javatter.javatter.auth.LoginUserDetailsService;
import org.javatter.javatter.converter.UserConverter;
import org.javatter.javatter.entity.User;
import org.javatter.javatter.form.UserForm;
import org.javatter.javatter.form.UserUpdateForm;
import org.javatter.javatter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private LoginUserDetailsService loginUserDetailsService;

    @Autowired
    private UserConverter userConverter;

    @GetMapping("")
    public String index(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "users/index";
    }

    @GetMapping("/new")
    public String newUser(Model model, Principal principal) {
        // 認証済ならリダイレクト
        if (principal != null)
            return "redirect:/users";

        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "users/new";
    }

    @PostMapping("")
    public String create(@Validated UserForm userForm, BindingResult bindingResult, Principal principal,
            HttpServletRequest request) {
        // 認証済ならリダイレクト
        if (principal != null)
            return "redirect:/users";
        // フォームのバリデーション
        if (bindingResult.hasErrors())
            return "users/new";
        // ユーザー登録
        userService.createUser(userForm);
        // ユーザー登録後、ログイン
        loginUserDetailsService.loginUser(request, userForm);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "users/show";
    }

    @RedirectNotCurrentUser
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        User user = userService.getUser(id);
        UserUpdateForm userUpdateForm = new UserUpdateForm();
        // エンティティの各データをフォームにセット
        userConverter.entityToForm(user, userUpdateForm);
        model.addAttribute("userUpdateForm", userUpdateForm);
        model.addAttribute("user", user);
        return "users/edit";
    }

    @RedirectNotCurrentUser
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Validated UserUpdateForm userUpdateForm, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            User user = userService.getUser(id);
            model.addAttribute("user", user);
            return "users/edit";
        }
        userService.updateUser(id, userUpdateForm);
        return String.format("redirect:/users/%d", id);
    }

    @RedirectNotCurrentUser
    @DeleteMapping("/{id}")
    public String destroy(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
