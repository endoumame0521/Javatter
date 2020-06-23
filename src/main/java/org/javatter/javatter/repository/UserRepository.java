package org.javatter.javatter.repository;

import java.util.Optional;

import org.javatter.javatter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // nullが返ってくるかもしれないのでOptional型で定義
    Optional<User> findByEmail(String email);
}
