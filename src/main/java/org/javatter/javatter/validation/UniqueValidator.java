package org.javatter.javatter.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.javatter.javatter.entity.User;
import org.javatter.javatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    @Autowired
    UserRepository userRepository;

    public void initialize(Unique constraintAnnotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        User user = userRepository.findByEmail(value).get();
        if (user == null) {
            return true;
        }
        return false;
    }
}
