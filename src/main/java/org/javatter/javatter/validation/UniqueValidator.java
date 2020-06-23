package org.javatter.javatter.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.javatter.javatter.entity.User;
import org.javatter.javatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    @Autowired
    UserRepository userRepository;

    public void initialize(Unique constraintAnnotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        // フォームで入力されたEmailの値を元にDBからユーザーを取得
        Optional<User> result = userRepository.findByEmail(value);
        User user = result.isPresent() ? result.get() : null;

        // 認証済ユーザーを取得
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String currentUserEmail = authentication.getName();

        // DBにemailが登録が未登録またはフォームのemailと認証済ユーザーのemailが同じ場合はtrue
        // ※新規登録時と更新時とでバリデーションの挙動が異なる為
        if (user == null || value.equals(currentUserEmail)) {
            return true;
        }
        return false;
    }
}
