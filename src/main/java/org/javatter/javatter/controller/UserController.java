package org.javatter.javatter.controller;

import java.util.List;

import org.javatter.javatter.annotation.ExcludeInterceptor;
import org.javatter.javatter.converter.UserConverter;
import org.javatter.javatter.entity.User;
import org.javatter.javatter.form.UserForm;
import org.javatter.javatter.form.UserUpdateForm;
import org.javatter.javatter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private UserConverter userConverter;

    @ExcludeInterceptor
    @GetMapping("")
    public String index(Model model) {
        List<User> users = userService.getUsers();
        model.addAttribute("users", users);
        return "users/index";
    }

    @ExcludeInterceptor
    @GetMapping("/new")
    public String newUser(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "users/new";
    }

    @ExcludeInterceptor
    @PostMapping("")
    public String create(@Validated UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        userService.createUser(userForm);
        return "redirect:/users";
    }

    @ExcludeInterceptor
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "users/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.getUser(id);

        UserUpdateForm userUpdateForm = new UserUpdateForm();
        // エンティティの各データをフォームにセット
        userConverter.entityToForm(user, userUpdateForm);
        model.addAttribute("userUpdateForm", userUpdateForm);
        return "users/edit";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Validated UserUpdateForm userUpdateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        userService.updateUser(id, userUpdateForm);
        return String.format("redirect:/users/%d", id);
    }

    @DeleteMapping("/{id}")
    public String destroy(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
